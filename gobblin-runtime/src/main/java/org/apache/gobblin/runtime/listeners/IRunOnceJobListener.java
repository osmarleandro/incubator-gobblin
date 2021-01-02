package org.apache.gobblin.runtime.listeners;

import org.apache.gobblin.runtime.JobContext;

public interface IRunOnceJobListener {

	void onJobCompletion(JobContext jobContext);

}