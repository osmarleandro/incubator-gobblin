package org.apache.gobblin.runtime.instance;

import java.util.List;
import java.util.Properties;

import org.apache.gobblin.broker.gobblin_scopes.GobblinScopeTypes;
import org.apache.gobblin.broker.iface.SharedResourcesBroker;
import org.apache.gobblin.metrics.MetricContext;
import org.apache.gobblin.metrics.Tag;
import org.apache.gobblin.runtime.api.Configurable;
import org.apache.gobblin.runtime.api.GobblinInstanceDriver;
import org.slf4j.Logger;

import com.typesafe.config.Config;

public interface IStandardGobblinInstanceLauncher {

	/** {@inheritDoc} */
	Config getConfig();

	/** {@inheritDoc} */
	Properties getConfigAsProperties();

	/** {@inheritDoc} */
	GobblinInstanceDriver getDriver() throws IllegalStateException;

	/** {@inheritDoc} */
	String getInstanceName();

	/** {@inheritDoc} */
	SharedResourcesBroker<GobblinScopeTypes> getInstanceBroker();

	MetricContext getMetricContext();

	boolean isInstrumentationEnabled();

	List<Tag<?>> generateTags(org.apache.gobblin.configuration.State state);

	void switchMetricContext(List<Tag<?>> tags);

	void switchMetricContext(MetricContext context);

	Logger getLog();

	Configurable getSysConfig();

}