package org.meanbean.factories;

import org.meanbean.lang.Factory;
import org.meanbean.util.ServiceDefinition;

import java.lang.reflect.Type;

/**
 * For looking up Factory instances
 */
public interface FactoryLookup {

	public static ServiceDefinition<FactoryLookup> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryLookup.class);
	}

	public static FactoryLookup getInstance() {
		return getServiceDefinition().getServiceFactory()
				.getFirst();
	}
	
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
	 * @param type
	 *            The type the Factory is registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return The requested Factory.
	 * 
	 * @throws IllegalArgumentException
	 *             If the class is deemed illegal.
	 * @throws NoSuchFactoryException
	 *             If the collection does not contain a Factory registered against the specified class.
	 */
	 <T> Factory<T> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException;

	/**
	 * Does the collection contain a Factory registered against the specified class?
	 * 
	 * @param type
	 *            The type a Factory could be registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return <code>true</code> if the collection contains a Factory registered for the specified class;
	 *         <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the clazz is deemed illegal.
	 */
	boolean hasFactory(Type clazz) throws IllegalArgumentException;
	
}