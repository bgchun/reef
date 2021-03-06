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
package org.apache.reef.tests.examples;

import org.apache.reef.examples.library.Command;
import org.apache.reef.examples.retained_eval.JobClient;
import org.apache.reef.examples.retained_eval.Launch;
import org.apache.reef.tang.Configuration;
import org.apache.reef.tang.Configurations;
import org.apache.reef.tang.Tang;
import org.apache.reef.tang.exceptions.InjectionException;
import org.apache.reef.tests.LocalTestEnvironment;
import org.apache.reef.tests.TestEnvironment;
import org.apache.reef.tests.TestEnvironmentFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * An integration test for retained evaluators: Run a simple `echo` on a couple of Evaluators a few times and make sure
 * it comes back.
 */
public final class TestRetainedEvaluators {
  /**
   * Message to print in (remote) shells.
   */
  private static final String MESSAGE = "Hello REEF";

  private final TestEnvironment testEnvironment = TestEnvironmentFactory.getNewTestEnvironment();

  /**
   * @return the Configuration for Launch for this test.
   */
  private static Configuration getLaunchConfiguration() {
    return Tang.Factory.getTang().newConfigurationBuilder()
        .bindNamedParameter(Launch.NumEval.class, "" + (LocalTestEnvironment.NUMBER_OF_THREADS - 1))
        .bindNamedParameter(Launch.NumRuns.class, "2")
        .bindNamedParameter(Command.class, "echo " + MESSAGE)
        .build();
  }

  @Before
  public void setUp() throws Exception {
    this.testEnvironment.setUp();
  }

  @After
  public void tearDown() throws Exception {
    this.testEnvironment.tearDown();
  }

  @Test
  public void testRetainedEvaluators() throws InjectionException {
    final Configuration clientConfiguration = Configurations.merge(
        JobClient.getClientConfiguration(),        // The special job client.
        getLaunchConfiguration(),                  // Specific configuration for this job
        testEnvironment.getRuntimeConfiguration()  // The runtime we shall use
    );

    final String result = Launch.run(clientConfiguration);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.contains(MESSAGE));
  }
}
