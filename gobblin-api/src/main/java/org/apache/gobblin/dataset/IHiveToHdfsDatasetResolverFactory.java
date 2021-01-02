package org.apache.gobblin.dataset;

import com.typesafe.config.Config;

public interface IHiveToHdfsDatasetResolverFactory {

	DatasetResolver createResolver(Config config);

}