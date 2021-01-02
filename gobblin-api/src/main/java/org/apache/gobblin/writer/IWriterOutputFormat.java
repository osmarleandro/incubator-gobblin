package org.apache.gobblin.writer;

public interface IWriterOutputFormat {

	/**
	   * Returns the file name extension for the enum type
	   * @return a string representation of the file name extension
	   */
	String getExtension();

}