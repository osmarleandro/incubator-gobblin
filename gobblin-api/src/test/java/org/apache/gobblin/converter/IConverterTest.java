package org.apache.gobblin.converter;

import org.testng.annotations.Test;

public interface IConverterTest {

	void testEmptyOutputIterable() throws Exception;

	void testSingleOutputIterable() throws Exception;

	void testMultiOutputIterable() throws Exception;

	void testMixedStream() throws Exception;

	void testAddRecordMetadata() throws Exception;

}