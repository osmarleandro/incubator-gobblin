package org.apache.gobblin.runtime.app;

import java.io.IOException;

import com.google.common.util.concurrent.Service;

public interface IServiceBasedAppLauncher {

	/**
	   * Starts the {@link ApplicationLauncher} by starting all associated services. This method also adds a shutdown hook
	   * that invokes {@link #stop()} and the {@link #close()} methods. So {@link #stop()} and {@link #close()} need not be
	   * called explicitly; they can be triggered during the JVM shutdown.
	   */
	void start();

	/**
	   * Stops the {@link ApplicationLauncher} by stopping all associated services.
	   */
	void stop() throws ApplicationException;

	void close() throws IOException;

	/**
	   * Add a {@link Service} to be run by this {@link ApplicationLauncher}.
	   *
	   * <p>
	   *   This method is public because there are certain classes launchers (such as Azkaban) that require the
	   *   {@link ApplicationLauncher} to extend a pre-defined class. Since Java classes cannot extend multiple classes,
	   *   composition needs to be used. In which case this method needs to be public.
	   * </p>
	   */
	void addService(Service service);

}