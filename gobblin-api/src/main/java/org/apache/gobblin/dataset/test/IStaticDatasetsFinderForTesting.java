package org.apache.gobblin.dataset.test;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.gobblin.dataset.Dataset;
import org.apache.hadoop.fs.Path;

public interface IStaticDatasetsFinderForTesting {

	List<Dataset> findDatasets() throws IOException;

	Path commonDatasetRoot();

	Iterator<Dataset> getDatasetsIterator() throws IOException;

	Stream<Dataset> getDatasetsStream(int desiredCharacteristics, Comparator<Dataset> suggestedOrder)
			throws IOException;

}