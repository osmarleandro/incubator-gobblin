package org.apache.gobblin.configuration;

import com.typesafe.config.Config;

public interface INoopDynamicConfigGenerator {

	Config generateDynamicConfig(Config config);

}