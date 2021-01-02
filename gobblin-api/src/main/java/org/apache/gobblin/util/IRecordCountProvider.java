package org.apache.gobblin.util;

import java.util.Collection;

import org.apache.hadoop.fs.Path;

public interface IRecordCountProvider {

	/**
	   * Get record count from a given {@link Path}.
	   */
	long getRecordCount(Path path);

	/**
	   * Convert a {@link Path} from another {@link RecordCountProvider} so that it can be used
	   * in {@link #getRecordCount(Path)} of this {@link RecordCountProvider}.
	   */
	Path convertPath(Path path, String extension, IRecordCountProvider src);

	/**
	   * Get record count for a list of paths.
	   */
	long getRecordCount(Collection<Path> paths);

}