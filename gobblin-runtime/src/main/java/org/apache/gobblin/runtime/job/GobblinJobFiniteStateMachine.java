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

package org.apache.gobblin.runtime.job;

import java.io.IOException;
import java.util.List;

import org.apache.gobblin.configuration.ConfigurationKeys;
import org.apache.gobblin.fsm.FiniteStateMachine;
import org.apache.gobblin.fsm.StateWithCallbacks;
import org.apache.gobblin.runtime.JobState;
import org.apache.gobblin.runtime.mapreduce.MRJobLauncher;
import org.apache.gobblin.source.workunit.MultiWorkUnit;
import org.apache.gobblin.source.workunit.WorkUnit;
import org.apache.gobblin.util.JobLauncherUtils;
import org.apache.gobblin.util.ParallelRunner;
import org.apache.hadoop.fs.Path;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.Closer;

import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * A {@link FiniteStateMachine} implementation to track the state of a Gobblin job executor.
 */
@Slf4j
public class GobblinJobFiniteStateMachine extends FiniteStateMachine<GobblinJobFiniteStateMachine.JobFSMState> {

	/**
	 * Types of state the job can be in.
	 */
	public enum StateType {
		PREPARING, RUNNING, INTERRUPTED, CANCELLED, SUCCESS, FAILED
	}

	/**
	 * State of a job.
	 */
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@EqualsAndHashCode(of = "stateType")
	@ToString
	@Getter
	public static class JobFSMState {
		private final StateType stateType;
	}

	/**
	 * A special {@link JobFSMState} that is aware of how to interrupt a running job.
	 */
	private class RunnableState extends JobFSMState implements StateWithCallbacks<JobFSMState> {
		private final JobInterruptionPredicate jobInterruptionPredicate;

		public RunnableState() {
			super(StateType.RUNNING);
			if (GobblinJobFiniteStateMachine.this.interruptGracefully == null) {
				this.jobInterruptionPredicate = null;
			} else {
				this.jobInterruptionPredicate = new JobInterruptionPredicate(GobblinJobFiniteStateMachine.this.jobState,
						GobblinJobFiniteStateMachine.this::interruptRunningJob, false);
			}
		}

		@Override
		public void onEnterState(@Nullable JobFSMState previousState) {
			if (this.jobInterruptionPredicate != null) {
				this.jobInterruptionPredicate.startAsync();
			}
		}

		@Override
		public void onLeaveState(JobFSMState nextState) {
			if (this.jobInterruptionPredicate != null) {
				this.jobInterruptionPredicate.stopAsync();
			}
		}

		@Override
		public boolean equals(Object o) {
			return super.equals(o);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

	/**
	 * A runnable that allows for {@link IOException}s.
	 */
	@FunctionalInterface
	public interface RunnableWithIoException {
		void run() throws IOException;
	}

	private final JobState jobState;
	private final RunnableWithIoException interruptGracefully;
	private final RunnableWithIoException killJob;

	@lombok.Builder
	private GobblinJobFiniteStateMachine(JobState jobState, RunnableWithIoException interruptGracefully,
			RunnableWithIoException killJob) {
		super(buildAllowedTransitions(), Sets.newHashSet(new JobFSMState(StateType.CANCELLED)), new JobFSMState(StateType.FAILED),
				new JobFSMState(StateType.PREPARING));

		if (jobState == null) {
			throw new IllegalArgumentException("Job state is required.");
		}

		this.jobState = jobState;
		this.interruptGracefully = interruptGracefully;
		this.killJob = killJob;
	}

	/**
	 * Callers should use this method to obtain the {@link JobFSMState} for a particular {@link StateType}, as the
	 * {@link JobFSMState} might contain additional functionality like running other services, etc.
	 * @param stateType
	 * @return
	 */
	public JobFSMState getEndStateForType(StateType stateType) {
		switch (stateType) {
			case RUNNING:
				return new RunnableState();
			default:
				return new JobFSMState(stateType);
		}
	}

	private void interruptRunningJob() {
		log.info("Interrupting job execution.");
		try (FiniteStateMachine<JobFSMState>.Transition transition = startTransition(getEndStateForType(StateType.INTERRUPTED))) {
				try {
					this.interruptGracefully.run();
				} catch (IOException ioe) {
					transition.changeEndState(getEndStateForType(StateType.FAILED));
				}
		} catch (FiniteStateMachine.UnallowedTransitionException exc) {
			log.error("Cannot interrupt job.", exc);
		} catch (InterruptedException | FailedTransitionCallbackException exc) {
			log.error("Cannot finish graceful job interruption. Killing job.", exc);
			try {
				this.killJob.run();
			} catch (IOException ioe) {
				log.error("Failed to kill job.", ioe);
			}
			if (exc instanceof FailedTransitionCallbackException) {
				((FailedTransitionCallbackException) exc).getTransition().switchEndStateToErrorState();
				((FailedTransitionCallbackException) exc).getTransition().closeWithoutCallbacks();
			}
		}
	}

	/**
	   * Prepare the job input.
	   * @param mrJobLauncher TODO
	 * @param workUnits TODO
	 * @throws IOException
	   */
	  public void prepareJobInput(MRJobLauncher mrJobLauncher, List<WorkUnit> workUnits) throws IOException {
	    Closer closer = Closer.create();
	    try {
	      ParallelRunner parallelRunner = closer.register(new ParallelRunner(mrJobLauncher.parallelRunnerThreads, mrJobLauncher.fs));
	
	      int multiTaskIdSequence = 0;
	      // Serialize each work unit into a file named after the task ID
	      for (WorkUnit workUnit : workUnits) {
	
	        String workUnitFileName;
	        if (workUnit instanceof MultiWorkUnit) {
	          workUnitFileName = JobLauncherUtils.newMultiTaskId(mrJobLauncher.jobContext.getJobId(), multiTaskIdSequence++)
	              + MRJobLauncher.MULTI_WORK_UNIT_FILE_EXTENSION;
	        } else {
	          workUnitFileName = workUnit.getProp(ConfigurationKeys.TASK_ID_KEY) + MRJobLauncher.WORK_UNIT_FILE_EXTENSION;
	        }
	        Path workUnitFile = new Path(mrJobLauncher.jobInputPath, workUnitFileName);
	        MRJobLauncher.LOG.debug("Writing work unit file " + workUnitFileName);
	
	        parallelRunner.serializeToFile(workUnit, workUnitFile);
	
	        // Append the work unit file path to the job input file
	      }
	    } catch (Throwable t) {
	      throw closer.rethrow(t);
	    } finally {
	      closer.close();
	    }
	  }

	private static SetMultimap<JobFSMState, JobFSMState> buildAllowedTransitions() {
		SetMultimap<JobFSMState, JobFSMState> transitions = HashMultimap.create();
		transitions.put(new JobFSMState(StateType.PREPARING), new JobFSMState(StateType.RUNNING));
		transitions.put(new JobFSMState(StateType.PREPARING), new JobFSMState(StateType.FAILED));
		transitions.put(new JobFSMState(StateType.PREPARING), new JobFSMState(StateType.INTERRUPTED));
		transitions.put(new JobFSMState(StateType.RUNNING), new JobFSMState(StateType.SUCCESS));
		transitions.put(new JobFSMState(StateType.RUNNING), new JobFSMState(StateType.FAILED));
		transitions.put(new JobFSMState(StateType.RUNNING), new JobFSMState(StateType.INTERRUPTED));
		return transitions;
	}
}
