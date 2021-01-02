package org.apache.gobblin.runtime.commit;

import java.io.IOException;

public interface IDatasetStateCommitStep {

	boolean isCompleted() throws IOException;

	void execute() throws IOException;

}