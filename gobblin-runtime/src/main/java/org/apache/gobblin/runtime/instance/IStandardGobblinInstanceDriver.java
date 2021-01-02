package org.apache.gobblin.runtime.instance;

import java.util.List;

import org.apache.gobblin.runtime.api.GobblinInstancePlugin;

public interface IStandardGobblinInstanceDriver {

	List<GobblinInstancePlugin> getPlugins();

}