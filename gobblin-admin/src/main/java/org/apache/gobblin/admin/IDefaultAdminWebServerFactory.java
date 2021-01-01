package org.apache.gobblin.admin;

import java.net.URI;
import java.util.Properties;

import com.google.common.util.concurrent.Service;

public interface IDefaultAdminWebServerFactory {

	/** {@inheritDoc} */
	Service createInstance(Properties config, URI executionInfoServerURI);

}