package org.apache.gobblin.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.gobblin.runtime.JobState.DatasetState;

public interface INoopDatasetStateStore {

	List<JobState.DatasetState> getAll(String storeName, String tableName) throws IOException;

	List<JobState.DatasetState> getAll(String storeName) throws IOException;

	Map<String, JobState.DatasetState> getLatestDatasetStatesByUrns(String jobName) throws IOException;

	void persistDatasetState(String datasetUrn, JobState.DatasetState datasetState) throws IOException;

	boolean create(String storeName) throws IOException;

	boolean create(String storeName, String tableName) throws IOException;

	boolean exists(String storeName, String tableName) throws IOException;

	void put(String storeName, String tableName, JobState.DatasetState state) throws IOException;

	void putAll(String storeName, String tableName, Collection<JobState.DatasetState> states) throws IOException;

	void createAlias(String storeName, String original, String alias) throws IOException;

	void delete(String storeName, String tableName) throws IOException;

	void delete(String storeName) throws IOException;

}