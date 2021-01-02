package org.apache.gobblin.runtime.fork;

import org.apache.gobblin.runtime.BoundedBlockingRecordQueue;
import org.apache.gobblin.runtime.BoundedBlockingRecordQueue.QueueStats;

import com.google.common.base.Optional;

public interface IAsynchronousFork {

	Optional<BoundedBlockingRecordQueue<Object>.QueueStats> queueStats();

}