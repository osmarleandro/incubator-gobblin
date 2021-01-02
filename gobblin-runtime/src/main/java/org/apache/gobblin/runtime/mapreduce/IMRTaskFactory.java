package org.apache.gobblin.runtime.mapreduce;

import org.apache.gobblin.publisher.DataPublisher;
import org.apache.gobblin.runtime.JobState;
import org.apache.gobblin.runtime.TaskContext;
import org.apache.gobblin.runtime.task.TaskIFace;

public interface IMRTaskFactory {

	TaskIFace createTask(TaskContext taskContext);

	DataPublisher createDataPublisher(JobState.DatasetState datasetState);

}