package org.meanbean.factories.util;

/**
 * Defines an object that provides access to a FactoryLookupStrategy.
 * 
 * @author Graham Williamson
 */
public interface FactoryLookupStrategyProvider {

	/**
	 * Get the FactoryLookupStrategy, which provides a means of acquiring Factories.
	 * 
	 * @return The factory lookup strategy.
	 */
	FactoryLookupStrategy getFactoryLookupStrategy();
}