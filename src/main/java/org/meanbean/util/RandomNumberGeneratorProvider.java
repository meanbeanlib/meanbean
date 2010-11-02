package org.meanbean.util;

/**
 * Defines an object that provides access to a RandomNumberGenerator.
 * 
 * @author Graham Williamson
 */
public interface RandomNumberGeneratorProvider {

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	RandomNumberGenerator getRandomNumberGenerator();

}
