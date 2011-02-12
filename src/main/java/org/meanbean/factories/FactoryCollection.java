package org.meanbean.factories;

import org.meanbean.lang.Factory;

/**
 * Defines a collection factories of different types of objects.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollection {

	/**
	 * <p>
	 * Add the specified Factory to the collection.
	 * </p>
	 * 
	 * <p>
	 * If a Factory is already registered against the specified class, the existing registered Factory will be replaced
	 * with the Factory you specify here.
	 * </p>
	 * 
	 * @param clazz
	 *            The type of objects the Factory creates. The class type will be used to generate a key with which the
	 *            Factory can be retrieved from the collection at a later stage.
	 * @param factory
	 *            The Factory to add to the collection.
	 * 
	 * @throws IllegalArgumentException
	 *             If either of the required parameters are deemed illegal.
	 */
	void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException;

	/**
	 * <p>
	 * Get the Factory registered for the specified class.
	 * </p>
	 * 
	 * <p>
	 * To check whether a Factory is registered for a specified class, please refer to
	 * <code>hasFactory(Class<?> clazz);</code>.
	 * </p>
	 * 
	 * @param clazz
	 *            The class the Factory is registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return The requested Factory.
	 * 
	 * @throws IllegalArgumentException
	 *             If the class is deemed illegal.
	 * @throws NoSuchFactoryException
	 *             If the collection does not contain a Factory registered against the specified class.
	 */
	Factory<?> getFactory(Class<?> clazz) throws IllegalArgumentException, NoSuchFactoryException;

	/**
	 * Does the collection contain a Factory registered against the specified class?
	 * 
	 * @param clazz
	 *            The class a Factory could be registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return <code>true</code> if the collection contains a Factory registered for the specified class;
	 *         <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the clazz is deemed illegal.
	 */
	boolean hasFactory(Class<?> clazz) throws IllegalArgumentException;
}