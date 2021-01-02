package org.apache.gobblin.fork;

import org.testng.annotations.Test;

public interface ICopyHelperTest {

	void testCopyable() throws CopyNotSupportedException;

	void testByteArray() throws CopyNotSupportedException;

	void testImmutables() throws CopyNotSupportedException;

	void testUnsupportedTypes() throws CopyNotSupportedException;

}