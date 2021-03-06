/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.reef.io.network.naming;

import org.apache.reef.tang.formats.ConfigurationModule;
import org.apache.reef.tang.formats.ConfigurationModuleBuilder;
import org.apache.reef.tang.formats.OptionalParameter;
import org.apache.reef.wake.IdentifierFactory;

/**
 * Configuration Module Builder for NameServer
 */
public final class NameServerConfiguration extends ConfigurationModuleBuilder {

  /**
   * The port used by name server
   */
  public static final OptionalParameter<Integer> NAME_SERVICE_PORT = new OptionalParameter<>();
  /**
   * DNS hostname running the name service
   */
  public static final OptionalParameter<String> NAME_SERVER_HOSTNAME = new OptionalParameter<>();
  /**
   * Identifier factory for the name service
   */
  public static final OptionalParameter<IdentifierFactory> NAME_SERVER_IDENTIFIER_FACTORY = new OptionalParameter<>();

  public static final ConfigurationModule CONF = new NameServerConfiguration()
      .bindNamedParameter(NameServerParameters.NameServerPort.class, NAME_SERVICE_PORT)
      .bindNamedParameter(NameServerParameters.NameServerAddr.class, NAME_SERVER_HOSTNAME)
      .bindNamedParameter(NameServerParameters.NameServerIdentifierFactory.class, NAME_SERVER_IDENTIFIER_FACTORY)
      .bindImplementation(NameServer.class, NameServerImpl.class)
      .build();
}
