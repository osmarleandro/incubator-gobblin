package org.apache.gobblin.source.workunit;

import java.util.List;

public interface IWorkUnitBinPacker {

	/**
	   * Packs the input {@link WorkUnit}s into {@link MultiWorkUnit}s.
	   * @param workUnitsIn List of {@link WorkUnit}s to pack.
	   * @param weighter {@link WorkUnitWeighter} that provides weights for {@link WorkUnit}s.
	   */
	List<WorkUnit> pack(List<WorkUnit> workUnitsIn, WorkUnitWeighter weighter);

}