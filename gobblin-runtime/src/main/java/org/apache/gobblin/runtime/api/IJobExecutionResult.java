package org.apache.gobblin.runtime.api;

public interface IJobExecutionResult {

	boolean isCancelled();

	boolean isSuccessful();

	boolean isFailed();

}