package org.apache.gobblin.runtime.mapreduce;

import java.io.IOException;

import javax.annotation.Nullable;

import org.apache.gobblin.runtime.JobException;
import org.apache.gobblin.runtime.app.ApplicationException;
import org.apache.gobblin.runtime.listeners.JobListener;

public interface ICliMRJobLauncher {

	int run(String[] args) throws Exception;

	void start() throws ApplicationException;

	void stop() throws ApplicationException;

	void launchJob(JobListener jobListener) throws JobException;

	void cancelJob(JobListener jobListener) throws JobException;

	void close() throws IOException;

}