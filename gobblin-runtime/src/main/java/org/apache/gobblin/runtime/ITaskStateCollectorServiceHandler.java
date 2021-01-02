package org.apache.gobblin.runtime;

import java.io.IOException;
import java.util.Collection;

import org.apache.gobblin.configuration.WorkUnitState;

public interface ITaskStateCollectorServiceHandler {

	/**
	   * Execute the actions of handler.
	   */
	void handle(Collection<? extends WorkUnitState> states) throws IOException;

}