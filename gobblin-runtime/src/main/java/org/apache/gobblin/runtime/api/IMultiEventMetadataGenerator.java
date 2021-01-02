package org.apache.gobblin.runtime.api;

import java.util.Map;

import org.apache.gobblin.metrics.event.EventName;
import org.apache.gobblin.runtime.JobContext;

public interface IMultiEventMetadataGenerator {

	Map<String, String> getMetadata(JobContext jobContext, EventName eventName);

}