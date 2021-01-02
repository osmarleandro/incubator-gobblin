package org.apache.gobblin.runtime.job_spec;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.api.JobTemplate;
import org.apache.gobblin.runtime.api.SpecNotFoundException;

import com.typesafe.config.ConfigException;

public interface IJobSpecResolver {

	/**
	 * Resolve an input {@link JobSpec} applying any templates.
	 *
	 * @throws SpecNotFoundException If no template exists with the specified URI.
	 * @throws JobTemplate.TemplateException If the template throws an exception during resolution.
	 * @throws ConfigException If the job config cannot be resolved.
	 */
	ResolvedJobSpec resolveJobSpec(JobSpec jobSpec)
			throws SpecNotFoundException, JobTemplate.TemplateException, ConfigException;

}