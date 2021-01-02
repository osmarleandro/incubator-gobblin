package org.apache.gobblin.runtime.job_monitor;

import java.net.URI;
import java.util.Collection;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.job_spec.AvroJobSpec;
import org.apache.gobblin.util.Either;

public interface IAvroJobSpecKafkaJobMonitor {

	/**
	   * Creates a {@link JobSpec} or {@link URI} from the {@link AvroJobSpec} record.
	   * @param record the record as an {@link AvroJobSpec}
	   * @return a {@link JobSpec} or {@link URI} wrapped in a {@link Collection} of {@link Either}
	   */
	Collection<Either<JobSpec, URI>> parseJobSpec(AvroJobSpec record);

}