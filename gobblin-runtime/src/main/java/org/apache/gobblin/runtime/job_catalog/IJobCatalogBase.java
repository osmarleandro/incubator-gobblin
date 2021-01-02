package org.apache.gobblin.runtime.job_catalog;

import java.util.List;

import org.apache.gobblin.metrics.MetricContext;
import org.apache.gobblin.metrics.Tag;
import org.apache.gobblin.runtime.api.JobCatalogListener;
import org.apache.gobblin.runtime.api.JobCatalog.StandardMetrics;

public interface IJobCatalogBase {

	/**{@inheritDoc}*/
	void addListener(JobCatalogListener jobListener);

	/**{@inheritDoc}*/
	void removeListener(JobCatalogListener jobListener);

	void registerWeakJobCatalogListener(JobCatalogListener jobListener);

	MetricContext getMetricContext();

	boolean isInstrumentationEnabled();

	List<Tag<?>> generateTags(org.apache.gobblin.configuration.State state);

	void switchMetricContext(List<Tag<?>> tags);

	void switchMetricContext(MetricContext context);

	StandardMetrics getMetrics();

}