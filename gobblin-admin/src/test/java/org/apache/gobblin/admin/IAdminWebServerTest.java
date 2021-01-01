package org.apache.gobblin.admin;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public interface IAdminWebServerTest {

	void startServer();

	void stopServer();

	void testGetSettingsJs() throws IOException;

	void testGetIndex() throws IOException;

}