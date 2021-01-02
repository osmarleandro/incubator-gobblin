package org.apache.gobblin.compat;

import java.io.IOException;

import org.testng.annotations.Test;

public interface ITextSerializerTest {

	void testSerialize() throws IOException;

	void testDeserialize() throws IOException;

}