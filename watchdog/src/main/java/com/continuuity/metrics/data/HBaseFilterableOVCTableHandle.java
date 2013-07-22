/*
 * Copyright 2012-2013 Continuuity,Inc. All Rights Reserved.
 */
package com.continuuity.metrics.data;

import com.continuuity.api.data.OperationException;
import com.continuuity.common.conf.CConfiguration;
import com.continuuity.data.engine.hbase.HBaseOVCTable;
import com.continuuity.data.engine.hbase.HBaseOVCTableHandle;
import com.continuuity.data.table.OrderedVersionedColumnarTable;
import com.google.inject.Inject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.client.HTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 */
public class HBaseFilterableOVCTableHandle extends HBaseOVCTableHandle implements TimeToLiveOVCTableHandle {

  private static final Logger LOG = LoggerFactory.getLogger(HBaseFilterableOVCTableHandle.class);

  @Inject
  public HBaseFilterableOVCTableHandle(CConfiguration conf, Configuration hConf) throws IOException {
    super(conf, hConf);
  }

  @Override
  public String getName() {
    return "hbase_filterable";
  }

  @Override
  protected HBaseOVCTable createOVCTable(byte[] tableName) throws OperationException {
    return new HBaseFilterableOVCTable(conf, hConf, tableName, FAMILY, new HBaseIOExceptionHandler());
  }

  @Override
  public OrderedVersionedColumnarTable getTable(byte[] tableName, int ttl) throws OperationException {
    OrderedVersionedColumnarTable table = this.openTables.get(tableName);

    // we currently have an open table for this name
    if (table != null) {
      return table;
    }

    // the table is not open, but it may exist in the data fabric
    table = openTable(tableName);

    // table could not be opened, try to create it
    if (table == null) {
      table = createNewTable(tableName, ttl);
    }

    // some other thread may have created/found and added it already
    OrderedVersionedColumnarTable existing =
      this.openTables.putIfAbsent(tableName, table);

    return existing != null ? existing : table;
  }

  protected OrderedVersionedColumnarTable createNewTable(byte[] tableName, int ttl) throws OperationException {
    try {
      createTable(tableName, FAMILY, ttl);
      return createOVCTable(tableName);
    } catch (IOException e) {
      exceptionHandler.handle(e);
    }
    return null;
  }

  protected HTable createTable(byte [] tableName, byte [] family, int ttl) throws IOException {
    if (this.admin.tableExists(tableName)) {
      LOG.debug("Attempt to creating table '" + tableName + "', which already exists. Opening existing table instead.");
      return new HTable(this.hConf, tableName);
    }
    HTableDescriptor htd = new HTableDescriptor(tableName);
    HColumnDescriptor hcd = new HColumnDescriptor(family);
    if (ttl > 0) {
      hcd.setTimeToLive(ttl);
    }
    htd.addFamily(hcd);
    try {
      LOG.info("Creating table '" + new String(tableName) + "'");
      this.admin.createTable(htd);
    } catch (TableExistsException e) {
      // table may exist because someone else is creating it at the same
      // time. But it may not be available yet, and opening it might fail.
      LOG.info("Creating table '" + new String(tableName) + "' failed with: "
                 + e.getMessage() + ".");
      // Wait at most 2 seconds for table to materialize
      long waitAtMost = 5000;
      long giveUpTime = System.currentTimeMillis() + waitAtMost;
      boolean exists = false;
      while (!exists && System.currentTimeMillis() < giveUpTime) {
        if (this.admin.tableExists(tableName)) {
          exists = true;
        } else {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e1) {
            LOG.error("Thread interrupted: " + e1.getMessage(), e1);
            break;
          }
        }
      }
      if (exists) {
        LOG.info("Table '" + new String(tableName) + "' exists now. Assuming " +
                   "that another process concurrently created it. ");
      } else {
        com.esotericsoftware.minlog.Log.error("Table '" + new String(tableName) + "' does not exist after" +
                                                " waiting " + waitAtMost + " ms. Giving up. ");
        throw e;
      }
    }
    return new HTable(this.hConf, tableName);
  }
}
