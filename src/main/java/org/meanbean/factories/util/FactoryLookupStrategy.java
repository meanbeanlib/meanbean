package org.meanbean.factories.util;

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTestException;
import org.meanbean.test.Configuration;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceDefinition;

/**
 * Defines a means of acquiring a Factory in the context of testing bean properties
 * 
 * @author Graham Williamson
 */
public interface FactoryLookupStrategy {

	public static ServiceDefinition<FactoryLookupStrategy> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryLookupStrategy.class,
				new Class<?>[] { FactoryCollection.class, RandomValueGenerator.class },
				new Object[] { FactoryCollection.getInstance(), RandomValueGenerator.getInstance() });
	}

	public static FactoryLookupStrategy getInstance() {
		return getServiceDefinition().getServiceFactory().getFirst();
	}

	/**
	 * <p>
	 * Get a factory for the specified property that is of the specified type. <br/>
	 * </p>
	 * 
	 * <p>
	 * If ultimately a suitable Factory cannot be found or created, a NoSuchFactoryException detailing the problem is
	 * thrown.
	 * </p>
	 * 
	 * @param beanInformation
	 *            Information about the bean the property belongs to.
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
	Factory<?> getFactory(BeanInformation beanInformation, String propertyName, Class<?> propertyType,
			Configuration configuration) throws IllegalArgumentException, BeanTestException;
}