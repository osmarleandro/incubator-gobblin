package org.apache.gobblin.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.gobblin.metastore.predicates.StateStorePredicate;
import org.apache.gobblin.runtime.JobState.DatasetState;
import org.apache.gobblin.runtime.metastore.filesystem.FsDatasetStateStoreEntryManager;

public interface IFsDatasetStateStore {

	String sanitizeDatasetStatestoreNameFromDatasetURN(String storeName, String datasetURN) throws IOException;

	JobState.DatasetState get(String storeName, String tableName, String stateId) throws IOException;

	JobState.DatasetState getInternal(String storeName, String tableName, String stateId,
			boolean sanitizeKeyForComparison) throws IOException;

	List<JobState.DatasetState> getAll(String storeName, String tableName) throws IOException;

	List<JobState.DatasetState> getAll(String storeName) throws IOException;

	/**
	   * Get a {@link Map} from dataset URNs to the latest {@link JobState.DatasetState}s.
	   *
	   * @param jobName the job name
	   * @return a {@link Map} from dataset URNs to the latest {@link JobState.DatasetState}s
	   * @throws IOException if there's something wrong reading the {@link JobState.DatasetState}s
	   */
	Map<String, JobState.DatasetState> getLatestDatasetStatesByUrns(String jobName) throws IOException;

	/**
	   * Get the latest {@link JobState.DatasetState} of a given dataset.
	   *
	   * @param storeName the name of the dataset state store
	   * @param datasetUrn the dataset URN
	   * @return the latest {@link JobState.DatasetState} of the dataset or {@link null} if it is not found
	   * @throws IOException
	   */
	JobState.DatasetState getLatestDatasetState(String storeName, String datasetUrn) throws IOException;

	/**
	   * Persist a given {@link JobState.DatasetState}.
	   *
	   * @param datasetUrn the dataset URN
	   * @param datasetState the {@link JobState.DatasetState} to persist
	   * @throws IOException if there's something wrong persisting the {@link JobState.DatasetState}
	   */
	void persistDatasetState(String datasetUrn, JobState.DatasetState datasetState) throws IOException;

	void persistDatasetURNs(String storeName, Collection<String> datasetUrns) throws IOException;

	List<FsDatasetStateStoreEntryManager> getMetadataForTables(StateStorePredicate predicate) throws IOException;

}