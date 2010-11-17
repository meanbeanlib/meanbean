package org.meanbean.test;

import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.lang.Factory;

/**
 * Defines a means of acquiring a Factory.
 * 
 * @author Graham Williamson
 */
public interface FactoryLookupStrategy {

	/**
	 * Get a factory for the specified property that is of the specified type. <br/>
	 * 
	 * If ultimately a suitable Factory cannot be found or created, a NoSuchFactoryException detailing the problem is
	 * thrown.
	 * 
	 * @param propertyName
	 *            The name of the property.
	 * @param propertyType
	 *            The type of the property.
	 * @param configuration
	 *            An optional Configuration object that may contain an override Factory for the specified property. Pass
	 *            <code>null</code> if no Configuration exists.
	 * 
	 * @return A Factory that may be used to create objects appropriate for the specified property.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the required parameters are deemed illegal. For example, if any are null.
	 * @throws NoSuchFactoryException
	 *             If an unexpected exception occurs when getting the Factory, including failing to find a suitable
	 *             Factory.
	 */
	Factory<?> getFactory(String propertyName, Class<?> propertyType, Configuration configuration)
	        throws IllegalArgumentException, BeanTestException;
}