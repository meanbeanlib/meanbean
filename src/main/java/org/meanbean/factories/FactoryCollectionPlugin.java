package org.meanbean.factories;

import org.meanbean.util.RandomValueGeneratorProvider;

/**
 * Defines a plugin that will register Factories with the specified FactoryCollection.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollectionPlugin {

	void initialize(FactoryCollection factoryCollection, RandomValueGeneratorProvider randomValueGeneratorProvider);
}