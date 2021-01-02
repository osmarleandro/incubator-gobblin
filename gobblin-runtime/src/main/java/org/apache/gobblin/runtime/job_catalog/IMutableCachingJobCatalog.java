package org.apache.gobblin.runtime.job_catalog;

import java.net.URI;

import org.apache.gobblin.runtime.api.JobSpec;

public interface IMutableCachingJobCatalog {

	/** {@inheritDoc} */
	void put(JobSpec jobSpec);

	/** {@inheritDoc} */
	void remove(URI uri);

}