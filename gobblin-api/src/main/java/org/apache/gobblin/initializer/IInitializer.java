package org.apache.gobblin.initializer;

public interface IInitializer {

	/**
	   * Initialize for the writer.
	   *
	   * @param state
	   * @param workUnits WorkUnits created by Source
	   */
	void initialize();

	/**
	   * Removed checked exception.
	   * {@inheritDoc}
	   * @see java.io.Closeable#close()
	   */
	void close();

}