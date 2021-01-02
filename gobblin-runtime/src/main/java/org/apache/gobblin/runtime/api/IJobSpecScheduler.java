package org.apache.gobblin.runtime.api;

import java.net.URI;
import java.util.Map;

public interface IJobSpecScheduler {

	/**
	   * Add a Gobblin job for scheduling. If the job is configured appropriately (scheduler-dependent),
	   * it will be executed repeatedly.
	   *
	   * @param   jobSpec     the JobSpec of the job
	   * @param   jobRunnable a runnable that will execute the job
	   */
	JobSpecSchedule scheduleJob(JobSpec jobSpec, Runnable jobRunnable);

	/**
	   * Add a Gobblin job for scheduling. Job is guaranteed to run only once regardless of job outcome.
	   *
	   * @param   jobSpec     the JobSpec of the job
	   * @param   jobRunnable a runnable that will execute the job
	   */
	JobSpecSchedule scheduleOnce(JobSpec jobSpec, Runnable jobRunnable);

	/**
	   * Remove a job from scheduling. This will not affect any executions that are currently running.
	   * @param jobSpecURI    the URI of the Gobblin job to unschedule.
	   * */
	void unscheduleJob(URI jobSpecURI);

	/** The outstanding job schedules. This is represents a snapshot in time and will not be updated
	   * as new schedules are added/removed.
	   * @return a map with the URIs of the scheduled jobs for keys and the schedules for values */
	Map<URI, JobSpecSchedule> getSchedules();

}