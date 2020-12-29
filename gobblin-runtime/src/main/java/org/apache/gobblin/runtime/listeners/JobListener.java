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

package org.apache.gobblin.runtime.listeners;

import javax.annotation.Nullable;

import org.apache.gobblin.runtime.JobContext;
import org.apache.gobblin.runtime.JobException;
import org.apache.gobblin.runtime.local.CliLocalJobLauncher;
import org.apache.gobblin.source.workunit.WorkUnit;

/**
 * An interface for classes used for callback on job state changes.
 */
public interface JobListener {

  /**
   * Called when a job is to be prepared, i.e. before determining the {@link WorkUnit}s.
   *
   * @param jobContext a {@link JobContext} object
   */
  void onJobPrepare(JobContext jobContext) throws Exception;

  /**
   * Called when a job is started, i.e. before the {@link WorkUnit}s are executed.
   *
   * @param jobContext a {@link JobContext} object
   */
  void onJobStart(JobContext jobContext) throws Exception;

  /**
   * Called when a job is completed.
   *
   * @param jobContext a {@link JobContext} object
   */
  void onJobCompletion(JobContext jobContext) throws Exception;

  /**
   * Called when a job is cancelled.
   *
   * @param jobContext a {@link JobContext} object
   */
  void onJobCancellation(JobContext jobContext) throws Exception;

  /**
   * Called when a job has failed.
   *
   * @param jobContext a {@link JobContext} object
   */
  void onJobFailure(JobContext jobContext) throws Exception;

default void launchJob(CliLocalJobLauncher cliLocalJobLauncher) throws JobException {
    cliLocalJobLauncher.localJobLauncher.launchJob(this);
  }
}
