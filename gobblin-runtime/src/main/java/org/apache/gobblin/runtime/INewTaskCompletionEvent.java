package org.apache.gobblin.runtime;

import java.util.Collection;

public interface INewTaskCompletionEvent {

	/**
	   * Get the {@link Collection} of {@link TaskState}s of completed {@link Task}s.
	   *
	   * @return the {@link Collection} of {@link TaskState}s of completed {@link Task}s
	   */
	Collection<TaskState> getTaskStates();

}