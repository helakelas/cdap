package com.continuuity.common.distributedservice;


import com.continuuity.common.utils.ImmutablePair;
import com.google.common.util.concurrent.Service;
import org.apache.hadoop.yarn.api.AMRMProtocol;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.Resource;

/**
 *
 */
public interface ApplicationMasterService extends Service {
  ApplicationMasterParameters getParameters();
  ApplicationAttemptId getApplicationAttemptId();
  AMRMProtocol getResourceManager();
  ImmutablePair<Resource, Resource> getClusterResourcesRange();
  ContainerManagerConnectionHandler getContainerManagerConnection();
  MasterConnectionHandler getMasterConnection();
}
