package org.apache.gobblin.dataset;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.fs.Path;

public interface IDatasetsFinder<T extends Dataset> {

	/**
	   * Find all {@link Dataset}s in the file system.
	   * @return List of {@link Dataset}s in the file system.
	   * @throws IOException
	   */
	List<T> findDatasets() throws IOException;

	/**
	   * @return The deepest common root shared by all {@link Dataset}s root paths returned by this finder.
	   */
	Path commonDatasetRoot();

}