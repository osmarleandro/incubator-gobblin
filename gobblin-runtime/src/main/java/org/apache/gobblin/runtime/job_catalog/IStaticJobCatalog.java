package org.apache.gobblin.runtime.job_catalog;

import java.net.URI;
import java.util.Collection;

import org.apache.gobblin.runtime.api.JobCatalogListener;
import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.api.JobSpecNotFoundException;

public interface IStaticJobCatalog {

	void addListener(JobCatalogListener jobListener);

	Collection<JobSpec> getJobs();

	void removeListener(JobCatalogListener jobListener);

	JobSpec getJobSpec(URI uri) throws JobSpecNotFoundException;

}