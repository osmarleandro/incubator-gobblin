package org.apache.gobblin.runtime.metastore.filesystem;

import java.io.IOException;

import org.apache.gobblin.runtime.JobState;

public interface IFsDatasetStateStoreEntryManager {

	JobState.DatasetState readState() throws IOException;

	void delete() throws IOException;

}