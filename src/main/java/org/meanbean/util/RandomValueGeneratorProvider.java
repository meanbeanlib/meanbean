package org.meanbean.util;

/**
 * Defines an object that provides access to a RandomValueGenerator.
 * 
 * @author Graham Williamson
 */
public interface RandomValueGeneratorProvider {

	/**
	 * Get a RandomValueGenerator.
	 * 
	 * @return A RandomValueGenerator.
	 */
	RandomValueGenerator getRandomValueGenerator();
}