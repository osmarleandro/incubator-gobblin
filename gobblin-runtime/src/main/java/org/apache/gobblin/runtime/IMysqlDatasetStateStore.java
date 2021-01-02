package org.apache.gobblin.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.gobblin.metastore.predicates.StateStorePredicate;
import org.apache.gobblin.runtime.JobState.DatasetState;
import org.apache.gobblin.runtime.metastore.mysql.MysqlDatasetStateStoreEntryManager;

public interface IMysqlDatasetStateStore {

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

	List<MysqlDatasetStateStoreEntryManager> getMetadataForTables(StateStorePredicate predicate) throws IOException;

}