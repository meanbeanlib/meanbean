package org.meanbean.factories;


/**
 * Defines an object that provides access to a FactoryCollection.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollectionProvider {

	/**
	 * The collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	FactoryCollection getFactoryCollection();
}