package org.apache.gobblin.runtime;

import org.apache.gobblin.metastore.DatasetStateStore;
import org.apache.gobblin.runtime.JobState.DatasetState;

import com.typesafe.config.Config;

public interface IFsDatasetStateStoreFactory {

	DatasetStateStore<JobState.DatasetState> createStateStore(Config config);

}