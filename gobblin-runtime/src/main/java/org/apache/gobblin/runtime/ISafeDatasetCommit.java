package org.apache.gobblin.runtime;

interface ISafeDatasetCommit {

	Void call() throws Exception;

}