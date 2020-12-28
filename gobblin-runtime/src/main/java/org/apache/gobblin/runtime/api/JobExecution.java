/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.gobblin.runtime.api;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.gobblin.annotation.Alpha;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * Identifies a specific execution of a {@link JobSpec}
 */
@Alpha
public interface JobExecution {
  /** The URI of the job being executed */
  URI getJobSpecURI();
  /** The version of the JobSpec being launched */
  String getJobSpecVersion();
  /** The millisecond timestamp when the job was launched */
  long getLaunchTimeMillis();
  /** Unique (for the given JobExecutionLauncher) id for this execution */
  String getExecutionId();
/**
   * Waits till a predicate on {@link #getRunningState()} becomes true or timeout is reached.
   *
   * @param jobExecutionState TODO
 * @param predicate               the predicate to evaluate. Note that even though the predicate
   *                                is applied on the entire object, it will be evaluated only when
   *                                the running state changes.
 * @param timeoutMs               the number of milliseconds to wait for the predicate to become
   *                                true; 0 means wait forever.
 * @throws InterruptedException   if the waiting was interrupted
   * @throws TimeoutException       if we reached the timeout before the predicate was satisfied.
   */
  default void awaitForStatePredicate(JobExecutionState jobExecutionState, Predicate<JobExecutionState> predicate, long timeoutMs)
         throws InterruptedException, TimeoutException {
    Preconditions.checkArgument(timeoutMs >= 0);
    if (0 == timeoutMs) {
      timeoutMs = Long.MAX_VALUE;
    }

    long startTimeMs = System.currentTimeMillis();
    long millisRemaining = timeoutMs;
    jobExecutionState.changeLock.lock();
    try {
      while (!predicate.apply(jobExecutionState) && millisRemaining > 0) {
        if (!jobExecutionState.runningStateChanged.await(millisRemaining, TimeUnit.MILLISECONDS)) {
          // Not necessary but shuts up FindBugs RV_RETURN_VALUE_IGNORED_BAD_PRACTICE
          break;
        }
        millisRemaining = timeoutMs - (System.currentTimeMillis() - startTimeMs);
      }

      if (!predicate.apply(jobExecutionState)) {
        throw new TimeoutException("Timeout waiting for state predicate: " + predicate);
      }
    }
    finally {
      jobExecutionState.changeLock.unlock();
    }
  }
}
