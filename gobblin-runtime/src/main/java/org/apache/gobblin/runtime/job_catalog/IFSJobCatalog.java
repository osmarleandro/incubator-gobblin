package org.apache.gobblin.runtime.job_catalog;

import java.net.URI;
import java.util.Collection;

import org.apache.gobblin.runtime.api.JobSpec;
import org.apache.gobblin.runtime.api.JobTemplate;
import org.apache.gobblin.runtime.api.SpecNotFoundException;
import org.apache.hadoop.fs.Path;

public interface IFSJobCatalog {

	/**
	   * Allow user to programmatically add a new JobSpec.
	   * The method will materialized the jobSpec into real file.
	   *
	   * @param jobSpec The target JobSpec Object to be materialized.
	   *                Noted that the URI return by getUri is a relative path.
	   */
	void put(JobSpec jobSpec);

	/**
	   * Allow user to programmatically delete a new JobSpec.
	   * This method is designed to be reentrant.
	   * @param jobURI The relative Path that specified by user, need to make it into complete path.
	   */
	void remove(URI jobURI);

	/**
	   * It is InMemoryJobCatalog's responsibility to inform the gobblin instance driver about the file change.
	   * Here it is internal detector's responsibility.
	   */
	boolean shouldLoadGlobalConf();

	Path getPathForURI(Path jobConfDirPath, URI uri);

	JobTemplate getTemplate(URI uri) throws SpecNotFoundException, JobTemplate.TemplateException;

	Collection<JobTemplate> getAllTemplates();

}