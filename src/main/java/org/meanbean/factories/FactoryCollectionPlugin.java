package org.meanbean.factories;

import org.meanbean.util.RandomValueGeneratorProvider;

/**
 * Defines a plugin that will register Factories with the specified FactoryCollection.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollectionPlugin {

	/**
	 * Initialize the plugin, adding Factories to the FactoryCollection.
	 * 
	 * @param factoryCollection
	 *            A FactoryCollection that Factory objects can be added to.
	 * @param randomValueGeneratorProvider
	 *            A RandomValueGeneratorProvider that provides access to a RandomValueGenerator that can be used by
	 *            Factory objects.
	 */
	void initialize(FactoryCollection factoryCollection, RandomValueGeneratorProvider randomValueGeneratorProvider);
}