package org.apache.gobblin.stream;

import java.io.IOException;

import org.apache.gobblin.configuration.WorkUnitState;
import org.apache.gobblin.metadata.GlobalMetadata;
import org.apache.gobblin.records.RecordStreamWithMetadata;
import org.apache.gobblin.records.RecordStreamProcessor.StreamProcessingException;

public interface IControlMessageInjector<SI, DI> {

	void close() throws IOException;

	/**
	   * Apply injections to the input {@link RecordStreamWithMetadata}.
	   * {@link ControlMessage}s may be injected before, after, or around the input record.
	   * A {@link MetadataUpdateControlMessage} will update the current input {@link GlobalMetadata} and pass the
	   * updated input {@link GlobalMetadata} to the next processor to propagate the metadata update down the pipeline.
	   */
	RecordStreamWithMetadata<DI, SI> processStream(RecordStreamWithMetadata<DI, SI> inputStream,
			WorkUnitState workUnitState) throws StreamProcessingException;

}