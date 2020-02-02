package org.meanbean.factories;

import org.meanbean.util.RandomValueGenerator;

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
	 * @param randomValueGenerator RandomValueGenerator that can be used by Factory objects.
	 */
	void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator);
}