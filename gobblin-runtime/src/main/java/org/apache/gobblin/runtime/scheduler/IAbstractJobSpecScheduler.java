package org.apache.gobblin.runtime.scheduler;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.api.JobSpecSchedule;
import org.apache.gobblin.runtime.api.JobSpecSchedulerListener;
import org.slf4j.Logger;

public interface IAbstractJobSpecScheduler {

	/** {@inheritDoc} */
	void registerJobSpecSchedulerListener(JobSpecSchedulerListener listener);

	/** {@inheritDoc} */
	void registerWeakJobSpecSchedulerListener(JobSpecSchedulerListener listener);

	/** {@inheritDoc} */
	void unregisterJobSpecSchedulerListener(JobSpecSchedulerListener listener);

	/** {@inheritDoc} */
	List<JobSpecSchedulerListener> getJobSpecSchedulerListeners();

	/** {@inheritDoc} */
	JobSpecSchedule scheduleJob(JobSpec jobSpec, Runnable jobRunnable);

	/** {@inheritDoc} */
	JobSpecSchedule scheduleOnce(JobSpec jobSpec, Runnable jobRunnable);

	/** {@inheritDoc} */
	void unscheduleJob(URI jobSpecURI);

	/** {@inheritDoc} */
	Map<URI, JobSpecSchedule> getSchedules();

	Logger getLog();

}