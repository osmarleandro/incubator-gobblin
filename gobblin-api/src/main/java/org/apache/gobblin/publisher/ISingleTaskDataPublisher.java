package org.apache.gobblin.publisher;

import java.io.IOException;

import org.apache.gobblin.configuration.WorkUnitState;

public interface ISingleTaskDataPublisher {

	/**
	   * Publish the data for the given task. The state must contains property "writer.final.output.file.paths"
	   * (or "writer.final.output.file.paths.[branchId]" if there are multiple branches),
	   * so that the publisher knows which files to publish.
	   */
	void publishData(WorkUnitState state) throws IOException;

	/**
	   * Publish the metadata (e.g., schema) for the given task. Checkpoints should not be published as part of metadata.
	   * They are published by Gobblin runtime after the metadata and data are published.
	   */
	void publishMetadata(WorkUnitState state) throws IOException;

	/**
	   * First publish the metadata via {@link DataPublisher#publishMetadata(WorkUnitState)}, and then publish the output data
	   * via the {@link DataPublisher#publishData(WorkUnitState)} method.
	   *
	   * @param state is a {@link WorkUnitState}.
	   * @throws IOException if there is a problem with publishing the metadata or the data.
	   */
	void publish(WorkUnitState state) throws IOException;

}