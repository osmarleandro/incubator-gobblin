package org.apache.gobblin.stream;

import org.testng.annotations.Test;

public interface IRecordEnvelopeTest {

	void testDerivedRecordCreation();

	void testMultipleDerivedRecords();

	void testClone();

	void testMultipleClones();

	void testRecordMetadata();

	void testRecordMetadataWithDerivedRecords();

	void testRecordMetadataWithClones();

}