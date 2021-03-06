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
package org.apache.reef.driver.evaluator;

import org.apache.reef.annotations.Provided;
import org.apache.reef.annotations.audience.DriverSide;
import org.apache.reef.annotations.audience.Public;
import org.apache.reef.driver.context.FailedContext;
import org.apache.reef.driver.task.FailedTask;
import org.apache.reef.exception.EvaluatorException;
import org.apache.reef.io.naming.Identifiable;
import org.apache.reef.util.Optional;

import java.util.List;

/**
 * Represents an Evaluator that became unavailable.
 */
@DriverSide
@Public
@Provided
public interface FailedEvaluator extends Identifiable {

  /**
   * @return the reason for the failure.
   */
  public EvaluatorException getEvaluatorException();

  /**
   * @return the list of all context that failed due to the evaluator failure.
   */
  public List<FailedContext> getFailedContextList();

  /**
   * @return the failed task, if there was one running at the time of the evaluator failure.
   */
  public Optional<FailedTask> getFailedTask();

}
