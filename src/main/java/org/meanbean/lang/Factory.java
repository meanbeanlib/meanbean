package org.meanbean.lang;

/**
 * Defines an object that creates objects of a specified type.
 * 
 * @author Graham Williamson
 * @param <T>
 *            The data type of the object this Factory creates.
 */
public interface Factory<T extends Object> {

	/**
	 * Create a new object of the specified type.
	 * 
	 * @return A new object of the specified type.
	 */
	T create();
}