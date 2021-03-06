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
package org.apache.reef.runtime.common.driver.context;


import org.apache.reef.annotations.audience.DriverSide;
import org.apache.reef.annotations.audience.Private;
import org.apache.reef.common.AbstractFailure;
import org.apache.reef.driver.context.ActiveContext;
import org.apache.reef.driver.context.FailedContext;
import org.apache.reef.driver.evaluator.EvaluatorDescriptor;
import org.apache.reef.util.Optional;

/**
 * Driver-Side representation of a failed context.
 */
@Private
@DriverSide
public final class FailedContextImpl extends AbstractFailure implements FailedContext {

  private final Optional<ActiveContext> parentContext;
  private final EvaluatorDescriptor evaluatorDescriptor;
  private final String evaluatorID;

  /**
   * @param id                  Identifier of the entity that produced the error.
   * @param message             One-line error message.
   * @param description         Long error description.
   * @param cause               Java Exception that caused the error.
   * @param data                byte array that contains serialized version of the error.
   * @param parentContext       the parent context, if there is one.
   * @param evaluatorDescriptor the descriptor of the Evaluator this context failed on.
   * @param evaluatorID         the id of the Evaluator this context failed on.
   */
  public FailedContextImpl(final String id,
                           final String message,
                           final Optional<String> description,
                           final Optional<Throwable> cause,
                           final Optional<byte[]> data,
                           final Optional<ActiveContext> parentContext,
                           final EvaluatorDescriptor evaluatorDescriptor,
                           final String evaluatorID) {
    super(id, message, description, cause, data);
    this.parentContext = parentContext;
    this.evaluatorDescriptor = evaluatorDescriptor;
    this.evaluatorID = evaluatorID;
  }


  @Override
  public Optional<ActiveContext> getParentContext() {
    return this.parentContext;
  }

  @Override
  public String getEvaluatorId() {
    return this.evaluatorID;
  }

  @Override
  public Optional<String> getParentId() {
    if (this.getParentContext().isPresent()) {
      return Optional.of(this.getParentContext().get().getId());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public EvaluatorDescriptor getEvaluatorDescriptor() {
    return this.evaluatorDescriptor;
  }


  @Override
  public String toString() {
    return "FailedContext{" + "evaluatorID='" + evaluatorID + "', contextID='" + getId() + "'}";
  }
}
