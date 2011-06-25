package org.meanbean.factories.util;

/**
 * Defines an object that generates IDs that can be used to identify a Factory uniquely.
 * 
 * @author Graham Williamson
 */
public interface FactoryIdGenerator {

	/**
	 * Create an ID for the specified class.
	 * 
	 * @param clazz
	 *            The class for which a unique ID should be generated.
	 * 
	 * @return A unique ID, generated for the specified class.
	 * 
	 * @throws IllegalArgumentException
	 *             If the clazz parameter is deemed illegal. For example, if it is null.
	 */
	String createIdFromClass(Class<?> clazz) throws IllegalArgumentException;

}