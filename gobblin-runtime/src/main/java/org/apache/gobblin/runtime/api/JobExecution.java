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

import org.apache.gobblin.annotation.Alpha;
import org.apache.gobblin.runtime.JobState.RunningState;

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
default void switchToRunning(JobExecutionState jobExecutionState) {
    jobExecutionState.doRunningStateChange(RunningState.RUNNING);
  }
}
