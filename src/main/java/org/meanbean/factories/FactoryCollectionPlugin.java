package org.meanbean.factories;

import org.meanbean.util.RandomNumberGeneratorProvider;

/**
 * Defines a plugin that will register Factories with the specified FactoryCollection.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollectionPlugin {

	void initialize(FactoryCollection factoryCollection, RandomNumberGeneratorProvider randomNumberGeneratorProvider);
}