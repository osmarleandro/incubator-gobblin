package org.apache.gobblin.runtime.job_catalog;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.gobblin.runtime.api.JobCatalogListener;
import org.apache.gobblin.runtime.api.JobSpec;
import org.slf4j.Logger;

import com.google.common.base.Function;

public interface IJobCatalogListenersList {

	Logger getLog();

	List<JobCatalogListener> getListeners();

	void addListener(JobCatalogListener newListener);

	void removeListener(JobCatalogListener oldListener);

	void onAddJob(JobSpec addedJob);

	void onDeleteJob(URI deletedJobURI, String deletedJobVersion);

	void onUpdateJob(JobSpec updatedJob);

	void onCancelJob(URI cancelledJobURI);

	void close() throws IOException;

	void callbackOneListener(Function<JobCatalogListener, Void> callback, JobCatalogListener listener);

	void registerWeakJobCatalogListener(JobCatalogListener jobListener);

}