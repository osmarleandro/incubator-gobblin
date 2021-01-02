package org.apache.gobblin.runtime.scheduler;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.api.JobSpecSchedule;

public interface IDefaultJobSpecSchedulerListenerImpl {

	/** {@inheritDoc} */
	void onJobScheduled(JobSpecSchedule jobSchedule);

	/** {@inheritDoc} */
	void onJobUnscheduled(JobSpecSchedule jobSchedule);

	/** {@inheritDoc} */
	void onJobTriggered(JobSpec jobSpec);

}