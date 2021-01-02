package org.apache.gobblin.runtime.locks;

import org.apache.gobblin.hive.HiveLockImpl;

public interface IDistributedHiveLockFactory {

	HiveLockImpl get(String name);

}