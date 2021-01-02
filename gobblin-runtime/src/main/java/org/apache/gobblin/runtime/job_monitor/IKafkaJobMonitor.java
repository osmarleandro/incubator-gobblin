package org.apache.gobblin.runtime.job_monitor;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.util.Either;

public interface IKafkaJobMonitor {

	/**
	   * @return A collection of either {@link JobSpec}s to add/update or {@link URI}s to remove from the catalog,
	   *        parsed from the Kafka message.
	   * @throws IOException
	   */
	Collection<Either<JobSpec, URI>> parseJobSpec(byte[] message) throws IOException;

}