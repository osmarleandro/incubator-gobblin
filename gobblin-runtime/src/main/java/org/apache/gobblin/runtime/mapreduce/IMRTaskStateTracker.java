package org.apache.gobblin.runtime.mapreduce;

import org.apache.gobblin.runtime.Task;

public interface IMRTaskStateTracker {

	void registerNewTask(Task task);

	void onTaskRunCompletion(Task task);

	void onTaskCommitCompletion(Task task);

}