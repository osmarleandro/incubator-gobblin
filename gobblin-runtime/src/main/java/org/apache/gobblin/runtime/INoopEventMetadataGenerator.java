package org.apache.gobblin.runtime;

import java.util.Map;

import org.apache.gobblin.metrics.event.EventName;

public interface INoopEventMetadataGenerator {

	Map<String, String> getMetadata(JobContext jobContext, EventName eventName);

}