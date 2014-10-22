/**
 * Copyright (C) 2014 Microsoft Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.reef.javabridge;

import com.microsoft.reef.driver.context.ActiveContext;
import com.microsoft.reef.driver.context.ClosedContext;
import com.microsoft.reef.driver.evaluator.EvaluatorDescriptor;
import com.microsoft.reef.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClosedContextBridge extends NativeBridge implements ClosedContext{

  private static final Logger LOG = Logger.getLogger(ClosedContextBridge.class.getName());

  private final ClosedContext jcloseContext;
  private final ActiveContextBridge parentContext;
  private final String contextId;
  private final String evaluatorId;
  private final EvaluatorDescriptor evaluatorDescriptor;

  public ClosedContextBridge(final ClosedContext closedContext)
  {
    jcloseContext = closedContext;
    parentContext = new ActiveContextBridge(closedContext.getParentContext());
    contextId = closedContext.getId();
    evaluatorId = closedContext.getEvaluatorId();
    evaluatorDescriptor = closedContext.getEvaluatorDescriptor();
  }

  @Override
  public String getId() {
    return contextId;
  }

  @Override
  public String getEvaluatorId() {
    return evaluatorId;
  }

  @Override
  public Optional<String> getParentId() {
    return Optional.of(parentContext.getId());
  }

  @Override
  public EvaluatorDescriptor getEvaluatorDescriptor() {
    return evaluatorDescriptor;
  }

  @Override
  public void close() throws Exception {
  }

  public String getEvaluatorDescriptorSring()
  {
    String descriptorString = Utilities.getEvaluatorDescriptorString(evaluatorDescriptor);
    LOG.log(Level.INFO, "Closed Context - serialized evaluator descriptor: " + descriptorString);
    return descriptorString;
  }

  @Override
  public ActiveContext getParentContext() {
    return jcloseContext.getParentContext();
  }
}