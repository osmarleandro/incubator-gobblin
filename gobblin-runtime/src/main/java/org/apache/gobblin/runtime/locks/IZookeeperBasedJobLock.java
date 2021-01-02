package org.apache.gobblin.runtime.locks;

import java.io.IOException;

public interface IZookeeperBasedJobLock {

	/**
	   * Sets the job lock listener.
	   *
	   * @param jobLockEventListener the listener for lock events
	   */
	void setEventListener(JobLockEventListener jobLockEventListener);

	/**
	   * Acquire the lock.
	   *
	   * @throws IOException
	   */
	void lock() throws JobLockException;

	/**
	   * Release the lock.
	   *
	   * @throws IOException
	   */
	void unlock() throws JobLockException;

	/**
	   * Try locking the lock.
	   *
	   * @return <em>true</em> if the lock is successfully locked,
	   *         <em>false</em> if otherwise.
	   * @throws IOException
	   */
	boolean tryLock() throws JobLockException;

	/**
	   * Check if the lock is locked.
	   *
	   * @return if the lock is locked
	   * @throws IOException
	   */
	boolean isLocked() throws JobLockException;

	/**
	   * Closes this stream and releases any system resources associated
	   * with it. If the stream is already closed then invoking this
	   * method has no effect.
	   *
	   * @throws IOException if an I/O error occurs
	   */
	void close() throws IOException;

}