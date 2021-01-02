package org.apache.gobblin.runtime.locks;

import java.io.IOException;

public interface IFileBasedJobLock {

	/**
	   * Acquire the lock.
	   *
	   * @throws JobLockException thrown if the {@link JobLock} fails to be acquired
	   */
	void lock() throws JobLockException;

	/**
	   * Release the lock.
	   *
	   * @throws JobLockException thrown if the {@link JobLock} fails to be released
	   */
	void unlock() throws JobLockException;

	/**
	   * Try locking the lock.
	   *
	   * @return <em>true</em> if the lock is successfully locked,
	   *         <em>false</em> if otherwise.
	   * @throws JobLockException thrown if the {@link JobLock} fails to be acquired
	   */
	boolean tryLock() throws JobLockException;

	/**
	   * Check if the lock is locked.
	   *
	   * @return if the lock is locked
	   * @throws JobLockException thrown if checking the status of the {@link JobLock} fails
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