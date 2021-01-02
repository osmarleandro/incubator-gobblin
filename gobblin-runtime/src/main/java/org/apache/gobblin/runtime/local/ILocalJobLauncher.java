package org.apache.gobblin.runtime.local;

import java.io.IOException;

public interface ILocalJobLauncher {

	void close() throws IOException;

}