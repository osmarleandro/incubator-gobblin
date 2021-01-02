package org.apache.gobblin.runtime.listeners;

import org.apache.gobblin.runtime.JobContext;

public interface IEmailNotificationJobListener {

	void onJobCompletion(JobContext jobContext);

	void onJobCancellation(JobContext jobContext);

}