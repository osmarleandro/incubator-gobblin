package org.apache.gobblin.dataset.comparators;

import org.apache.gobblin.dataset.URNIdentified;

public interface IURNLexicographicalComparator {

	int compare(URNIdentified o1, URNIdentified o2);

	/**
	   * Compare against a raw URN.
	   */
	int compare(URNIdentified o1, String urn2);

	/**
	   * Compare against a raw URN.
	   */
	int compare(String urn1, URNIdentified o2);

}