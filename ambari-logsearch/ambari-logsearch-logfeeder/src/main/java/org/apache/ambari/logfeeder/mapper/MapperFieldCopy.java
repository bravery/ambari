/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ambari.logfeeder.mapper;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Overrides the value for the field
 */
public class MapperFieldCopy extends Mapper {
  private static final Logger LOG = Logger.getLogger(MapperFieldCopy.class);
  
  private String copyName = null;

  @Override
  public boolean init(String inputDesc, String fieldName, String mapClassCode, Object mapConfigs) {
    init(inputDesc, fieldName, mapClassCode);
    if (!(mapConfigs instanceof Map)) {
      LOG.fatal("Can't initialize object. mapConfigs class is not of type Map. " + mapConfigs.getClass().getName());
      return false;
    }
    
    @SuppressWarnings("unchecked")
    Map<String, Object> mapObjects = (Map<String, Object>) mapConfigs;
    copyName = (String) mapObjects.get("copy_name");
    if (StringUtils.isEmpty(copyName)) {
      LOG.fatal("Map copy name is empty.");
      return false;
    }
    return true;
  }

  @Override
  public Object apply(Map<String, Object> jsonObj, Object value) {
    jsonObj.put(copyName, value);
    return value;
  }
}
