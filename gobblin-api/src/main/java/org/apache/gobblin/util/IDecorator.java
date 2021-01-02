package org.apache.gobblin.util;

public interface IDecorator {

	/**
	   * Get directly underlying object.
	   * @return directly underlying object.
	   */
	Object getDecoratedObject();

}