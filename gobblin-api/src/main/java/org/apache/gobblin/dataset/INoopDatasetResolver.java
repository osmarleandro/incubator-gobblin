package org.apache.gobblin.dataset;

import org.apache.gobblin.configuration.State;

public interface INoopDatasetResolver {

	DatasetDescriptor resolve(DatasetDescriptor raw, State state);

	Descriptor resolve(Descriptor raw, State state);

}