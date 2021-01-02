package org.apache.gobblin.testing;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public interface IAssertWithBackoffTest {

	void setUp();

	void testComputeRetrySleep();

	void testAssertWithBackoff_conditionTrue() throws Exception;

	void testAssertWithBackoff_conditionEventuallyTrue() throws Exception;

	void testAssertWithBackoff_conditionFalse() throws Exception;

	void testAssertWithBackoff_RuntimeException() throws Exception;

}