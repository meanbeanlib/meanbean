package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.factory.DynamicBeanFactory;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.basic.EnumFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete FactoryLookupStrategy that implements the following Factory lookup algorithm:
 * 
 * <ol>
 * <li>If a Configuration is provided, this is first inspected for a property-specific Factory.</li>
 * 
 * <li>If no Configuration is provided or there is no property-specific Factory in the Configuration, the
 * FactoryCollection is then searched for a Factory suitable for the type of the property.</li>
 * 
 * <li>If the FactoryCollection does not contain a suitable Factory, an attempt is made to create a Factory for the
 * type. For example, if the type is an Enum, then a generic Enum Factory will be created for the Enum's constants,
 * registered in the Factory Collection for future use, and returned from this method. As a last resort, an attempt is
 * made create a Factory that creates objects of the custom data type. If successful, this Factory is registered in the
 * Factory Collection for future use, and return from this method.</li>
 * 
 * <li>If ultimately a suitable Factory cannot be found or created, a NoSuchFactoryException detailing the problem is
 * thrown.</li>
 * </ol>
 * 
 * @author Graham Williamson
 */
class BasicFactoryLookupStrategy implements FactoryLookupStrategy {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(BasicFactoryLookupStrategy.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator;

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection;

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory;

	/**
	 * Construct a new Factory Lookup Strategy.
	 * 
	 * @param factoryCollection
	 *            A collection of test data Factories.
	 * @param randomValueGenerator
	 *            Random number generator used by factories to randomly generate values.
	 * 
	 * @throws IllegalArgumentException
	 *             If either the factoryCollection or randomValueGenerator are deemed illegal. For example, if either is
	 *             <code>null</code>.
	 */
	public BasicFactoryLookupStrategy(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator)
	        throws IllegalArgumentException {
		validationHelper.ensureExists("factoryCollection", "construct FactoryLookupStrategy", factoryCollection);
		validationHelper.ensureExists("randomValueGenerator", "construct FactoryLookupStrategy", randomValueGenerator);
		this.factoryCollection = factoryCollection;
		this.randomValueGenerator = randomValueGenerator;
		beanInformationFactory = new JavaBeanInformationFactory();
	}

	/**
	 * Get a factory for the specified property that is of the specified type. <br/>
	 * 
	 * If a Configuration is provided, this is first inspected for a property-specific Factory. <br/>
	 * 
	 * If no Configuration is provided or there is no property-specific Factory in the Configuration, the
	 * FactoryCollection is then searched for a Factory suitable for the type of the property. <br/>
	 * 
	 * If the FactoryCollection does not contain a suitable Factory, an attempt is made to create a Factory for the
	 * type. <br/>
	 * 
	 * For example, if the type is an Enum, then a generic Enum Factory will be created for the Enum's constants,
	 * registered in the Factory Collection for future use, and returned from this method. <br/>
	 * 
	 * As a last resort, an attempt is made create a Factory that creates objects of the custom data type. If
	 * successful, this Factory is registered in the Factory Collection for future use, and return from this method. <br/>
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
	@Override
	public Factory<?> getFactory(String propertyName, Class<?> propertyType, Configuration configuration)
	        throws IllegalArgumentException, NoSuchFactoryException {
		log.debug("getFactory: entering with propertyName=[" + propertyName + "], propertyType=[" + propertyType
		        + "], configuration=[" + configuration + "].");
		// Validate
		validationHelper.ensureExists("propertyName", "get factory", propertyName);
		validationHelper.ensureExists("propertyType", "get factory", propertyType);
		// Get factory
		Factory<?> result;
		if ((configuration != null) && (configuration.hasOverrideFactory(propertyName))) {
			// Use the Factory override in the Configuration
			log.debug("getFactory: Configuration has a Factory override for propertyType.");
			result = configuration.getOverrideFactory(propertyName);
		} else if (factoryCollection.hasFactory(propertyType)) {
			// Use the Factory registered in the FactoryCollection
			log.debug("getFactory: FactoryCollection has a Factory registed for propertyType.");
			result = factoryCollection.getFactory(propertyType);
		} else if (propertyType.isEnum()) {
			// Try to create a Factory for the Enum
			log.debug("getFactory: propertyType is an enum. Try to create a generic EnumFactory.");
			EnumFactory enumFactory = new EnumFactory(propertyType, randomValueGenerator);
			factoryCollection.addFactory(propertyType, enumFactory);
			result = enumFactory;
		} else {
			// Try to create a Factory for the object
			log.debug("getFactory: Try to create a DynamicBeanFactory for propertyType=[" + propertyType + "].");
			try {
				Factory<?> dynamicFactory = new DynamicBeanFactory(beanInformationFactory.create(propertyType));
				dynamicFactory.create(); // Test the factory before registering and returning
				factoryCollection.addFactory(propertyType, dynamicFactory);
				log.warn("Using DynamicBeanFactory for [" + propertyName + "] of type [" + propertyType.getName()
				        + "]. Do you need to register a custom Factory?");
				result = dynamicFactory;
			} catch (Exception e) {
				String message = "Failed to find suitable Factory for property=[" + propertyName + "] of type=["
				        + propertyType + "]. Please register a custom Factory.";
				log.error("getFactory: " + message + " Throw NoSuchFactoryException.", e);
				throw new NoSuchFactoryException(message, e);
			}
		}
		log.debug("getFactory: exiting returning [" + result + "].");
		return result;
	}
}