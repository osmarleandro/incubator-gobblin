package org.apache.gobblin.runtime.listeners;

import org.apache.gobblin.runtime.JobContext;

public interface IAbstractJobListener {

	void onJobPrepare(JobContext jobContext) throws Exception;

	void onJobStart(JobContext jobContext) throws Exception;

	void onJobCompletion(JobContext jobContext) throws Exception;

	void onJobCancellation(JobContext jobContext) throws Exception;

	void onJobFailure(JobContext jobContext) throws Exception;

}