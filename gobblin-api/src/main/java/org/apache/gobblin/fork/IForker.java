package org.apache.gobblin.fork;

import org.apache.gobblin.configuration.WorkUnitState;
import org.apache.gobblin.fork.Forker.ForkedStream;
import org.apache.gobblin.records.RecordStreamWithMetadata;

import io.reactivex.Flowable;

public interface IForker {

	/**
	   * Obtain the {@link ForkedStream} for the input {@link RecordStreamWithMetadata} and {@link ForkOperator}.
	   * @param inputStream input {@link Flowable} of records.
	   * @param forkOperator {@link ForkOperator} specifying the fork behavior.
	   * @param workUnitState work unit configuration.
	   * @return a {@link ForkedStream} with the forked streams.
	   * @throws Exception if the {@link ForkOperator} throws any exceptions.
	   */
	<D, S> ForkedStream<D, S> forkStream(RecordStreamWithMetadata<D, S> inputStream, ForkOperator<S, D> forkOperator,
			WorkUnitState workUnitState) throws Exception;

}