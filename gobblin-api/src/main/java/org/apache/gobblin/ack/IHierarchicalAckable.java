package org.apache.gobblin.ack;

public interface IHierarchicalAckable {

	/**
	   * @return A new child {@link Ackable} that must be acked before parents are acked.
	   */
	Ackable newChildAckable();

	/**
	   * Indicates no more children will be created.
	   */
	void close();

}