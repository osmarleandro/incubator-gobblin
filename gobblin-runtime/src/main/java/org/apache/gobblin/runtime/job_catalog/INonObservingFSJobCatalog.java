package org.apache.gobblin.runtime.job_catalog;

import java.net.URI;

import org.apache.gobblin.runtime.api.JobSpec;

public interface INonObservingFSJobCatalog {

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

	void remove(URI jobURI, boolean alwaysTriggerListeners);

}