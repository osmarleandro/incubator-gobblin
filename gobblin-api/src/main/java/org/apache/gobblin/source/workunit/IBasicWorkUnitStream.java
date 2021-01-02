package org.apache.gobblin.source.workunit;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public interface IBasicWorkUnitStream {

	Iterator<WorkUnit> getWorkUnits();

	/**
	   * Apply a transformation function to this stream.
	   */
	WorkUnitStream transform(Function<WorkUnit, WorkUnit> function);

	/**
	   * Apply a filtering function to this stream.
	   */
	WorkUnitStream filter(Predicate<WorkUnit> predicate);

	/**
	   * Get a materialized collection of the {@link WorkUnit}s in this stream. Note this call will fail if
	   * {@link #isSafeToMaterialize()} is false.
	   */
	Collection<WorkUnit> getMaterializedWorkUnitCollection();

}