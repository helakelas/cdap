/*
 * Copyright © 2015 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package co.cask.cdap.common.namespace;

import co.cask.cdap.common.conf.CConfiguration;
import co.cask.cdap.common.conf.Constants;
import co.cask.cdap.proto.Id;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.inject.Inject;
import org.apache.twill.filesystem.Location;
import org.apache.twill.filesystem.LocationFactory;

import java.io.IOException;
import javax.annotation.Nullable;

/**
 * Default implementation of {@link NamespacedLocationFactory}
 */
public class DefaultNamespacedLocationFactory implements NamespacedLocationFactory {

  private final LocationFactory locationFactory;
  private final String namespaceDir;
  private final NamespaceAdmin nsAdmin;

  @Inject
  public DefaultNamespacedLocationFactory(CConfiguration cConf, LocationFactory locationFactory, NamespaceAdmin
    nsAdmin) {
    this.namespaceDir = cConf.get(Constants.Namespace.NAMESPACES_DIR);
    this.locationFactory = locationFactory;
    this.nsAdmin = nsAdmin;
  }

  @Override
  public Location get(Id.Namespace namespaceId) throws IOException {
    return get(namespaceId, null);
  }

  @Override
  public Location get(Id.Namespace namespaceId, @Nullable String subPath) throws IOException {
    String hdfsDirectory = null;
    try {
      hdfsDirectory = nsAdmin.get(namespaceId).getConfig().getHdfsDirectory();
    } catch (Exception e) {
      Throwables.propagate(e);
    }
    Location namespaceLocation;
    if (Strings.isNullOrEmpty(hdfsDirectory)) {
      namespaceLocation = locationFactory.create(namespaceDir).append(namespaceId.getId());
    } else {
      namespaceLocation = getBaseLocation().append(hdfsDirectory);
    }
    if (subPath != null) {
      namespaceLocation = namespaceLocation.append(subPath);
    }
    return namespaceLocation;
  }

  @Override
  public Location getBaseLocation() throws IOException {
    return locationFactory.create("/");
  }
}
