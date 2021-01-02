package org.apache.gobblin.runtime;

import java.io.IOException;
import java.util.Collection;

import org.apache.gobblin.configuration.WorkUnitState;

public interface IHiveRegTaskStateCollectorServiceHandlerImpl {

	void handle(Collection<? extends WorkUnitState> taskStates) throws IOException;

	void close() throws IOException;

}