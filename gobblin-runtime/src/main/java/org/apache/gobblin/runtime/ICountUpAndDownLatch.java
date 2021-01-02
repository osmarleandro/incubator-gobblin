package org.apache.gobblin.runtime;

import java.util.concurrent.TimeUnit;

interface ICountUpAndDownLatch {

	void await() throws InterruptedException;

	boolean await(long timeout, TimeUnit unit) throws InterruptedException;

	void countDown();

	void countUp();

	long getCount();

	long getRegisteredParties();

	String toString();

}