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
package com.microsoft.reef.runtime.common.driver.runtime;

import com.microsoft.reef.proto.DriverRuntimeProtocol;
import com.microsoft.reef.proto.ReefServiceProtos;
import com.microsoft.wake.EventHandler;
import com.microsoft.wake.time.Clock;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the status of the Runtime (YARN, Local, ...)
 */
public final class RuntimeStatusManager implements EventHandler<DriverRuntimeProtocol.RuntimeStatusProto> {
  private static final Logger LOG = Logger.getLogger(RuntimeStatusManager.class.getName());
  private final String name = "REEF";
  private final Clock clock;
  private final RuntimeErrorHandler runtimeErrorHandler;

  // Mutable state.
  private ReefServiceProtos.State state = ReefServiceProtos.State.INIT;
  private int outstandingContainerRequests = 0;
  private int containerAllocationCount = 0;

  @Inject
  RuntimeStatusManager(final Clock clock, final RuntimeErrorHandler runtimeErrorHandler) {
    this.clock = clock;
    this.runtimeErrorHandler = runtimeErrorHandler;
  }


  @Override
  public synchronized void onNext(DriverRuntimeProtocol.RuntimeStatusProto runtimeStatusProto) {
    final ReefServiceProtos.State newState = runtimeStatusProto.getState();
    LOG.log(Level.FINEST, "Runtime status " + runtimeStatusProto);
    this.outstandingContainerRequests = runtimeStatusProto.getOutstandingContainerRequests();
    this.containerAllocationCount = runtimeStatusProto.getContainerAllocationCount();
    this.setState(runtimeStatusProto.getState());

    switch (newState) {
      case FAILED:
        this.runtimeErrorHandler.onNext(runtimeStatusProto.getError());
        break;
      case DONE:
        this.clock.close();
        break;
      case RUNNING:
        if (this.isIdle()) {
          this.clock.close();
        }
        break;
    }
  }

  public synchronized void setRunning() {
    this.setState(ReefServiceProtos.State.RUNNING);
  }

  public synchronized boolean isIdle() {
    return this.clock.isIdle()
        && this.hasNoOutstandingRequests()
        && this.hasNoContainersAllocated();
  }

  public synchronized boolean isRunning() {
    return ReefServiceProtos.State.RUNNING.equals(this.state);
  }

  public synchronized boolean isRunningAndIdle() {
    return isRunning() && isIdle();
  }


  private synchronized void setState(ReefServiceProtos.State state) {
    // TODO: Add state transition check
    this.state = state;
  }


  private synchronized boolean hasNoOutstandingRequests() {
    return this.outstandingContainerRequests == 0;
  }

  private synchronized boolean hasNoContainersAllocated() {
    return this.containerAllocationCount == 0;
  }

}
