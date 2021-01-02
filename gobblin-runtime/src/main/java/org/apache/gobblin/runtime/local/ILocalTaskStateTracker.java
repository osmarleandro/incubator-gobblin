package org.apache.gobblin.runtime.local;

import org.apache.gobblin.runtime.Task;

public interface ILocalTaskStateTracker {

	void registerNewTask(Task task);

	void onTaskRunCompletion(Task task);

	void onTaskCommitCompletion(Task task);

}