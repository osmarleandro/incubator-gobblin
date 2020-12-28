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

package org.apache.gobblin.runtime;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.math3.util.Pair;
import org.apache.gobblin.broker.gobblin_scopes.GobblinScopeTypes;
import org.apache.gobblin.broker.gobblin_scopes.TaskScopeInstance;
import org.apache.gobblin.broker.iface.SubscopedBrokerBuilder;
import org.apache.gobblin.configuration.ConfigurationKeys;
import org.apache.gobblin.configuration.WorkUnitState;
import org.apache.gobblin.metrics.event.EventSubmitter;
import org.apache.gobblin.metrics.event.EventSubmitter.Builder;
import org.apache.gobblin.metrics.event.JobEvent;
import org.apache.gobblin.runtime.util.JobMetrics;
import org.apache.gobblin.runtime.util.JobMetrics.CreatorTag;
import org.apache.gobblin.source.workunit.WorkUnit;

import com.github.rholder.retry.RetryException;
import com.google.common.collect.Lists;


/**
 * A {@link CountDownLatch} that allows counting up. Backed by a {@link Phaser}.
 */
class CountUpAndDownLatch extends CountDownLatch {

  private final Phaser phaser;

  public CountUpAndDownLatch(int count) {
    super(0);
    this.phaser = new Phaser(count) {
      @Override
      protected boolean onAdvance(int phase, int registeredParties) {
        // Need to override onAdvance because phaser by default terminates whenever registered parties reaches 0
        return false;
      }
    };
  }

  @Override
  public void await() throws InterruptedException {
    int phase = getPhase();
    this.phaser.awaitAdvance(phase);
  }

  @Override
  public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
    try {
      int phase = getPhase();
      this.phaser.awaitAdvanceInterruptibly(phase, timeout, unit);
      return true;
    } catch (TimeoutException te) {
      return false;
    }
  }

  private int getPhase() {
    int phase = this.phaser.register();
    this.phaser.arriveAndDeregister();
    return phase;
  }

  @Override
  public void countDown() {
    this.phaser.arriveAndDeregister();
  }

  public void countUp() {
    this.phaser.register();
  }

  @Override
  public long getCount() {
    return this.phaser.getUnarrivedParties();
  }

  public long getRegisteredParties() {
    return this.phaser.getRegisteredParties();
  }

  @Override
  public String toString() {
    return "Unarrived parties: " + this.phaser.getUnarrivedParties();
  }

/**
   * Run a given list of {@link WorkUnit}s of a job.
   *
   * <p>
   *   This method assumes that the given list of {@link WorkUnit}s have already been flattened and
   *   each {@link WorkUnit} contains the task ID in the property {@link ConfigurationKeys#TASK_ID_KEY}.
   * </p>
   *
   * @param gobblinMultiTaskAttempt TODO
 * @return a list of {@link Task}s from the {@link WorkUnit}s, as well as if there's a failure in task creation
   * which should be handled separately to avoid silently starving on certain workunit.
   */
  Pair<List<Task>, Boolean> runWorkUnits(GobblinMultiTaskAttempt gobblinMultiTaskAttempt) {

    List<Task> tasks = Lists.newArrayList();

    // A flag indicating if there are any tasks not submitted successfully.
    // Caller of this method should handle tasks with submission failures accordingly.
    boolean areAllTasksSubmitted = true;
    while (gobblinMultiTaskAttempt.workUnits.hasNext()) {
      WorkUnit workUnit = gobblinMultiTaskAttempt.workUnits.next();
      String taskId = workUnit.getProp(ConfigurationKeys.TASK_ID_KEY);

      // skip tasks that executed successfully in a prior attempt
      if (gobblinMultiTaskAttempt.taskSuccessfulInPriorAttempt(taskId)) {
        continue;
      }

      SubscopedBrokerBuilder<GobblinScopeTypes, ?> taskBrokerBuilder =
          gobblinMultiTaskAttempt.jobBroker.newSubscopedBuilder(new TaskScopeInstance(taskId));
      WorkUnitState workUnitState = new WorkUnitState(workUnit, gobblinMultiTaskAttempt.jobState, taskBrokerBuilder);
      workUnitState.setId(taskId);
      workUnitState.setProp(ConfigurationKeys.JOB_ID_KEY, gobblinMultiTaskAttempt.jobId);
      workUnitState.setProp(ConfigurationKeys.TASK_ID_KEY, taskId);
      workUnitState.setProp(ConfigurationKeys.TASK_START_TIME_MILLIS_KEY, Long.toString(System.currentTimeMillis()));

      if (gobblinMultiTaskAttempt.containerIdOptional.isPresent()) {
        workUnitState.setProp(ConfigurationKeys.TASK_ATTEMPT_ID_KEY, gobblinMultiTaskAttempt.containerIdOptional.get());
      }

      // Create a new task from the work unit and submit the task to run.
      // If an exception occurs here then the count down latch is decremented
      // to avoid being stuck waiting for a task that was not created and submitted successfully.
      Task task = null;
      try {
        countUp();
        task = gobblinMultiTaskAttempt.createTaskWithRetry(workUnitState, this);
        gobblinMultiTaskAttempt.taskStateTracker.registerNewTask(task);
        task.setTaskFuture(gobblinMultiTaskAttempt.taskExecutor.submit(task));
        tasks.add(task);
      } catch (Throwable e) {
        if (e instanceof OutOfMemoryError) {
          gobblinMultiTaskAttempt.log.error("Encountering memory error in task creation/execution stage, please investigate memory usage:", e);
          gobblinMultiTaskAttempt.printMemoryUsage();
        }

        if (task == null) {
          if (e instanceof RetryException) {
            // Indicating task being null due to failure in creation even after retrying.
            areAllTasksSubmitted = false;
          }
          // task could not be created, so directly count down
          countDown();
          gobblinMultiTaskAttempt.log.error("Could not create task for workunit {}", workUnit, e);
        } else if (!task.hasTaskFuture()) {
          // Task was created and may have been registered, but not submitted, so call the
          // task state tracker task run completion directly since the task cancel does nothing if not submitted
          gobblinMultiTaskAttempt.taskStateTracker.onTaskRunCompletion(task);
          areAllTasksSubmitted = false;
          gobblinMultiTaskAttempt.log.error("Could not submit task for workunit {}", workUnit, e);
        } else {
          // task was created and submitted, but failed later, so cancel the task to decrement the CountDownLatch
          task.cancel();
          gobblinMultiTaskAttempt.log.error("Failure after task submitted for workunit {}", workUnit, e);
        }
      }
    }

    Builder eventSubmitterBuilder = new Builder(JobMetrics.get(gobblinMultiTaskAttempt.jobId, new CreatorTag(gobblinMultiTaskAttempt.attemptId)).getMetricContext(),
        "gobblin.runtime");
    eventSubmitterBuilder.addMetadata(gobblinMultiTaskAttempt.taskEventMetadataGenerator.getMetadata(gobblinMultiTaskAttempt.jobState, JobEvent.TASKS_SUBMITTED));
    eventSubmitterBuilder.build().submit(JobEvent.TASKS_SUBMITTED, "tasksCount", Long.toString(getRegisteredParties()));

    return new Pair<>(tasks, areAllTasksSubmitted);
  }
}
