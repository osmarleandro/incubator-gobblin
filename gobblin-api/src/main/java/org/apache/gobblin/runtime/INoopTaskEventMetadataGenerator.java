package org.apache.gobblin.runtime;

import java.util.Map;

import org.apache.gobblin.configuration.State;

public interface INoopTaskEventMetadataGenerator {

	/**
	   * Generate a map of additional metadata for the specified event name.
	   *
	   * @param taskState
	   * @param eventName the event name used to determine which additional metadata should be emitted
	   * @return {@link Map} with the additional metadata
	   */
	Map<String, String> getMetadata(State taskState, String eventName);

}