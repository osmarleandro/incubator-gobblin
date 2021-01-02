package org.apache.gobblin.dataset;

import java.io.IOException;
import java.util.Iterator;

public interface IIterableDatasetFinderImpl<T extends Dataset> {

	Iterator<T> getDatasetsIterator() throws IOException;

}