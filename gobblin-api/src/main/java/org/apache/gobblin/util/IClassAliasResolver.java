package org.apache.gobblin.util;

import java.util.List;
import java.util.Map;

import org.apache.gobblin.annotation.Alias;

public interface IClassAliasResolver<T> {

	/**
	   * Resolves the given alias to its name if a mapping exits. To create a mapping for a class,
	   * it has to be annotated with {@link Alias}
	   *
	   * @param possibleAlias to use for resolution.
	   * @return The name of the class with <code>possibleAlias</code> if mapping is available.
	   * Return the input <code>possibleAlias</code> if no mapping is found.
	   */
	String resolve(String possibleAlias);

	/**
	   * Attempts to resolve the given alias to a class. It first tries to find a class in the classpath with this alias
	   * and is also a subclass of {@link #subtypeOf}, if it fails it returns a class object for name
	   * <code>aliasOrClassName</code>.
	   */
	Class<? extends T> resolveClass(String aliasOrClassName) throws ClassNotFoundException;

	/**
	   * Get the map from found aliases to classes.
	   */
	Map<String, Class<? extends T>> getAliasMap();

	List<Alias> getAliasObjects();

}