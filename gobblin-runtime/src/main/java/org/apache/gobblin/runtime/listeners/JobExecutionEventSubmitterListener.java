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

import lombok.AllArgsConstructor;

import org.apache.gobblin.runtime.JobContext;
import org.apache.gobblin.runtime.JobExecutionEventSubmitter;


@AllArgsConstructor

/**
 * Implementation of {@link JobListener} that submits metadata events via {@link JobExecutionEventSubmitter} when a job
 * is completed or is cancelled.
 */
public class JobExecutionEventSubmitterListener extends AbstractJobListener {

  private final JobExecutionEventSubmitter jobExecutionEventSubmitter;

  @Override
  public void onJobCompletion(JobContext jobContext) {
    jobContext.getJobState().submitJobExecutionEvents(this.jobExecutionEventSubmitter);
  }

  @Override
  public void onJobCancellation(JobContext jobContext) {
    jobContext.getJobState().submitJobExecutionEvents(this.jobExecutionEventSubmitter);
  }
}
