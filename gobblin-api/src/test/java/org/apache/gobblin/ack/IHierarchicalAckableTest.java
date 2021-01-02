package org.apache.gobblin.ack;

import org.testng.annotations.Test;

public interface IHierarchicalAckableTest {

	void testCloseBeforeAck() throws Exception;

	void testAckBeforeClose() throws Exception;

	void testChildNacked() throws Exception;

	void testMultipleParents() throws Exception;

}