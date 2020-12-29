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
package org.apache.gobblin.runtime.scheduler;

import org.slf4j.Logger;

import com.google.common.base.Optional;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.api.JobSpecSchedule;
import org.apache.gobblin.runtime.api.JobSpecSchedulerListener;

/**
 * Default no-op implementation for {@link JobSpecSchedulerListener}. It may optionally log
 * the callbacks.
 */
public class DefaultJobSpecSchedulerListenerImpl implements JobSpecSchedulerListener {
  public final Optional<Logger> _log;

  /**
   * Constructor
   * @param log   if no log is specified, the logging will be done. Logging level is INFO.
   */
  public DefaultJobSpecSchedulerListenerImpl(Optional<Logger> log) {
    _log = log;
  }

  public DefaultJobSpecSchedulerListenerImpl(Logger log) {
    this(Optional.of(log));
  }

  /** Constructor with no logging */
  public DefaultJobSpecSchedulerListenerImpl() {
    this(Optional.<Logger>absent());
  }

  /** {@inheritDoc} */
  @Override public void onJobScheduled(JobSpecSchedule jobSchedule) {
    if (_log.isPresent()) {
      _log.get().info("Job scheduled: " + jobSchedule);
    }
  }

  /** {@inheritDoc} */
  @Override public void onJobUnscheduled(JobSpecSchedule jobSchedule) {
	jobSchedule.onJobUnscheduled(this);
}

  /** {@inheritDoc} */
  @Override public void onJobTriggered(JobSpec jobSpec) {
    if (_log.isPresent()) {
      _log.get().info("Job triggered: " + jobSpec);
    }
  }

}
