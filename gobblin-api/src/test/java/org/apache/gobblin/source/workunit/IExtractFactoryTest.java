package org.apache.gobblin.source.workunit;

import org.testng.annotations.Test;

public interface IExtractFactoryTest {

	/**
	   * Verify that each {@link Extract} created by an {@ExtractFactory} has a unique ID.
	   */
	void testGetUniqueExtract();

}