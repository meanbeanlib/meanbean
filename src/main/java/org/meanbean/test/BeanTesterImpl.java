package org.meanbean.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.factories.Factory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.factories.basic.EnumFactory;
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.SimpleRandomNumberGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete BeanTester, affording functionality to test JavaBean objects.
 * 
 * @author Graham Williamson
 */
public class BeanTesterImpl implements BeanTester {

	/** The number of times each bean is tested, unless a custom Configuration overrides this global setting. */
	private int iterations = BeanTester.TEST_ITERATIONS_PER_BEAN;

	/** Random number generator used by factories to randomly generate values. */
	private final RandomNumberGenerator randomNumberGenerator = new SimpleRandomNumberGenerator();

	/** The repository of test data Factories. */
	private final FactoryCollection factoryRepository = new FactoryRepository(randomNumberGenerator);

	/** Custom Configurations that override standard testing behaviour on a per-type basis across all tests. */
	private final Map<Class<?>, Configuration> customConfigurations = Collections
	        .synchronizedMap(new HashMap<Class<?>, Configuration>());

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory;

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(BeanTesterImpl.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/**
	 * Construct a new Bean Tester implementation.
	 */
	public BeanTesterImpl() {
		log.debug("BeanTesterImpl: entering.");
		beanInformationFactory = new JavaBeanInformationFactory();
		log.debug("BeanTesterImpl: exiting.");
	}

	/**
	 * The repository of test data Factories with which you can register Factories for custom Data Types.
	 * 
	 * @return The repository of test data Factories.
	 */
	@Override
	public FactoryCollection getFactoryRepository() {
		return factoryRepository;
	}

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	@Override
	public RandomNumberGenerator getRandomNumberGenerator() {
		return randomNumberGenerator;
	}

	/**
	 * Set the number of times each bean should be tested, globally. <br/>
	 * 
	 * Note: A custom Configuration can override this global test setting.
	 * 
	 * @param iterations
	 *            The number of times each bean should be tested. This value must be at least 1.
	 * 
	 * @throws IllegalArgumentException
	 *             If the iterations parameter is deemed illegal. For example, if it is less than 1.
	 */
	@Override
	public void setIterations(int iterations) throws IllegalArgumentException {
		log.debug("setIterations: entering with iterations=[" + iterations + "].");
		if (iterations < 1) {
			log.debug("setIterations: Iterations must be at least 1. Throw IllegalArgumentException.");
			throw new IllegalArgumentException("Iterations must be at least 1.");
		}
		this.iterations = iterations;
		log.debug("setIterations: exiting.");
	}

	/**
	 * Get the number of times each bean should be tested. <br/>
	 * 
	 * Note: A custom Configuration can override this global setting.
	 * 
	 * @return The number of times each bean should be tested. This value will be at least 1.
	 */
	@Override
	public int getIterations() {
		return iterations;
	}

	/**
	 * Add the specified Configuration as a custom Configuration to be used as an override to any global configuration
	 * settings when testing the type specified by the beanClass parameter.
	 * 
	 * @param beanClass
	 *            The type that the Configuration should be used for during testing.
	 * @param configuration
	 *            The custom Configuration, to be used only when testing the beanClass type.
	 * 
	 * @throws IllegalArgumentException
	 *             If either parameter is deemed illegal. For example, if either parameter is null.
	 */
	@Override
	public void addCustomConfiguration(Class<?> beanClass, Configuration configuration) throws IllegalArgumentException {
		log.debug("addCustomConfiguration: entering with beanClass=[" + beanClass + "], configuration=["
		        + configuration + "].");
		validationHelper.ensureExists("beanClass", "add custom configuration", beanClass);
		validationHelper.ensureExists("configuration", "add custom configuration", configuration);
		customConfigurations.put(beanClass, configuration);
		log.debug("addCustomConfiguration: exiting.");
	}

	/**
	 * Does the specified type have a custom Configuration registered?
	 * 
	 * @param beanClass
	 *            The type for which a Configuration is sought.
	 * 
	 * @return <code>true</code> if a custom Configuration has been registered for the type specified by the beanClass
	 *         parameter; <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass parameter is deemed illegal. For example, if it is null.
	 */
	protected boolean hasCustomConfiguration(Class<?> beanClass) throws IllegalArgumentException {
		log.debug("hasCustomConfiguration: entering with beanClass=[" + beanClass + "].");
		validationHelper.ensureExists("beanClass", "check for custom configuration", beanClass);
		boolean result = customConfigurations.containsKey(beanClass);
		log.debug("hasCustomConfiguration: exiting returning [" + result + "].");
		return result;
	}

	/**
	 * Get the custom Configuration registered against the specified type.
	 * 
	 * @param beanClass
	 *            The type for which a Configuration is sought.
	 * 
	 * @return A custom Configuration registered against the type specified by the beanClass parameter, if one exists;
	 *         <code>null</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass parameter is deemed illegal. For example, if it is null.
	 */
	protected Configuration getCustomConfiguration(Class<?> beanClass) throws IllegalArgumentException {
		log.debug("getCustomConfiguration: entering with beanClass=[" + beanClass + "].");
		validationHelper.ensureExists("beanClass", "get custom configuration", beanClass);
		Configuration result = customConfigurations.get(beanClass);
		log.debug("getCustomConfiguration: exiting returning [" + result + "].");
		return result;
	}

	/**
	 * Test the type specified by the beanClass parameter.
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass is deemed illegal. For example, if it is null.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	@Override
	public void test(Class<?> beanClass) throws BeanTestException {
		log.debug("test: entering with beanClass=[" + beanClass + "].");
		validationHelper.ensureExists("beanClass", "test", beanClass);
		Configuration customConfiguration = null;
		if (hasCustomConfiguration(beanClass)) {
			customConfiguration = getCustomConfiguration(beanClass);
		}
		test(beanClass, customConfiguration);
		log.debug("test: exiting.");
	}

	/**
	 * Test the type specified by the beanClass parameter, using the custom Configuration provided as an override to any
	 * global configuration settings.
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * @param configuration
	 *            The custom Configuration to be used when testing the specified beanClass type. This Configuration is
	 *            only used for this individual test and will not be retained for future testing of this type. To
	 *            register a custom Configuration across multiple tests, use <code>addCustomConfiguration()</code>. If
	 *            no custom Configuration is required, pass <code>null</code>.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass parameter is deemed illegal. For example, if it is null.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	@Override
	public void test(Class<?> beanClass, Configuration customConfiguration) throws IllegalArgumentException,
	        BeanTestException {
		log.debug("test: entering with beanClass=[" + beanClass + "], customConfiguration=[" + customConfiguration
		        + "].");
		validationHelper.ensureExists("beanClass", "test", beanClass);
		doTest(beanClass, getIterations(), customConfiguration);
		log.debug("test: exiting.");
	}

	/**
	 * Test the type specified by the beanClass parameter using the specified Configuration. The type will be tested
	 * however many times are specified in the iterations parameter.
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * @param iterations
	 *            The number of times the type should be tested. This should be at least 1. The Configuration can
	 *            override this value.
	 * @param configuration
	 *            The Configuration to be used during the test. If no custom Configuration is required, pass
	 *            <code>null</code>.
	 * 
	 * @throws IllegalArgumentException
	 *             If either the beanClass or iterations parameter is deemed illegal. For example, if the beanClass is
	 *             null or iterations is less than 1.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	private void doTest(Class<?> beanClass, int iterations, Configuration configuration)
	        throws IllegalArgumentException, BeanTestException {
		log.debug("doTest: entering with beanClass=[" + beanClass + "], iterations=[" + iterations
		        + "], configuration=[" + configuration + "].");
		// Validate
		validationHelper.ensureExists("beanClass", "test", beanClass);
		if (iterations < 1) {
			log.debug("doTest: Cannot test beanClass less than 1 times. Throw IllegalArgumentException.");
			throw new IllegalArgumentException("Cannot test beanClass less than 1 times.");
		}
		// Get all information about a potential JavaBean class
		BeanInformation beanInformation = beanInformationFactory.create(beanClass);
		// Override the standard number of iterations if need be
		if ((configuration != null) && (configuration.hasIterationsOverride())) {
			iterations = configuration.getIterations();
		}
		// Test the JavaBean 'iterations' times
		for (int idx = 0; idx < iterations; idx++) {
			log.debug("doTest: Iteration [" + idx + "].");
			doTest(beanInformation, configuration);
		}
		log.debug("doTest: exiting.");
	}

	/**
	 * Test the type specified by the beanInformation parameter using the specified Configuration.
	 * 
	 * @param beanInformation
	 *            Information about the type to be tested.
	 * @param configuration
	 *            The Configuration to be used during the test. If no custom Configuration is required, pass
	 *            <code>null</code>.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanInformation parameter is deemed illegal. For example, if it is null.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	private void doTest(BeanInformation beanInformation, Configuration configuration) throws IllegalArgumentException,
	        BeanTestException {

		log.debug("doTest: entering with beanInformation=[" + beanInformation + "], configuration=[" + configuration
		        + "].");

		validationHelper.ensureExists("beanInformation", "test", beanInformation);

		// Get all properties of the bean
		Collection<PropertyInformation> properties = beanInformation.getProperties();

		// Get just the properties of the bean that are readable and writable
		Collection<PropertyInformation> readableWritableProperties = PropertyInformationFilter.filter(properties,
		        PropertyVisibility.READABLE_WRITABLE);

		// Instantiate
		Object bean = createBean(beanInformation);

		// Test each property
		for (PropertyInformation property : readableWritableProperties) {
			// Skip testing any 'ignored' properties
			if ((configuration == null) || (!configuration.isIgnoredProperty(property.getName()))) {
				doTest(bean, property, configuration);
			}
		}

		log.debug("doTest: exiting.");
	}

	/**
	 * Test the property specified by the propertyInformation parameter on the specified object using the specified
	 * Configuration.
	 * 
	 * @param bean
	 *            The object the property should be tested on.
	 * @param property
	 *            Information about the property to be tested.
	 * 
	 * @param configuration
	 *            The Configuration to be used during the test. If no custom Configuration is required, pass
	 *            <code>null</code>.
	 * 
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	private void doTest(Object bean, PropertyInformation property, Configuration configuration)
	        throws BeanTestException {

		log.debug("doTest: entering with bean=[" + bean + "], property=[" + property + "], configuration=["
		        + configuration + "].");

		// Validation
		validationHelper.ensureExists("bean", "test", bean);
		validationHelper.ensureExists("property", "test", property);
		// validationHelper.ensureExists("configuration", "test", configuration);

		// Get write method information
		Method writeMethod = property.getWriteMethod();
		Class<?>[] parameterTypes = writeMethod.getParameterTypes();
		if (parameterTypes.length != 1) {
			String message = "writeMethod " + property.getName() + "." + writeMethod.getName()
			        + " must accept only 1 paramter, but accepts [" + parameterTypes.length
			        + "] parameters. It is not a JavaBean property.";
			log.debug("doTest: " + message + " Throw BeanTestException.");
			throw new BeanTestException(message);
		}
		Class<?> parameterType = parameterTypes[0];

		// Create test data
		Factory<?> factory = getFactory(property.getName(), parameterType, configuration);
		Object testValue = factory.create();

		// Invoke setter
		doInvokeMethod(bean, writeMethod, testValue);

		// Get read method information
		Method readMethod = property.getReadMethod();
		Class<?> returnType = readMethod.getReturnType();
		if (!returnType.isAssignableFrom(parameterType)) {
			String message = "readMethod " + property.getName() + "." + readMethod.getName()
			        + " must return same type as writeMethod " + property.getName() + "." + writeMethod.getName()
			        + " accepts. It is not a JavaBean property.";
			log.debug("doTest: " + message + " Throw BeanTestException.");
			throw new BeanTestException(message);
		}

		// Invoke getter
		Object readMethodOutput = doInvokeMethod(bean, readMethod);

		// Compare results
		doCompare(testValue, readMethodOutput);

		log.debug("doTest: exiting.");
	}

	/**
	 * Compare the specified parameters to assess whether the actual value matches the expected value.
	 * 
	 * @param expected
	 *            The expected value.
	 * @param actual
	 *            The obtained value.
	 */
	private void doCompare(Object expected, Object actual) {
		log.debug("doCompare: entering with expected=[" + expected + "], actual=[" + actual + "].");
		if (expected == null) {
			if (actual != null) {
				log.debug("doCompare: [FAIL] Getter did not return test value. Expected [" + expected
				        + "] but getter returned [" + actual + "]");
			} else {
				log.debug("doCompare: [PASS] Expected [null] == getter returned [null].");
			}
		} else if (!expected.equals(actual)) {
			log.debug("doCompare: [FAIL] Getter did not return test value. Expected [" + expected
			        + "] but getter returned [" + actual + "]");
		} else {
			log.debug("doCompare: [PASS] Expected [" + expected + "] == getter returned [" + actual + "].");
		}
		log.debug("doCompare: exiting.");
	}

	/**
	 * Create an instance of the type specified in beanInformation.
	 * 
	 * @param beanInformation
	 *            Information about the object to instantiate.
	 * 
	 * @return An instance of the type specified in beanInformation.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanInformation parameter is deemed illegal. For example, if it is null.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs when instantiating the bean.
	 */
	private Object createBean(BeanInformation beanInformation) throws IllegalArgumentException, BeanTestException {
		log.debug("createBean: entering with beanInformation=[" + beanInformation + "].");
		validationHelper.ensureExists("beanInformation", "create bean", beanInformation);
		validationHelper.ensureExists("beanInformation.beanClass", "create bean", beanInformation.getBeanClass());
		try {
			Object result = beanInformation.getBeanClass().newInstance();
			log.debug("createBean: exiting returning [" + result + "].");
			return result;
		} catch (InstantiationException e) {
			log.debug("createBean: Failed to instantiate bean of type [" + beanInformation.getBeanClass().getName()
			        + "] due to InstantiationException. Throw BeanTestException.", e);
			throw new BeanTestException("Failed to instantiate bean of type ["
			        + beanInformation.getBeanClass().getName() + "] due to InstantiationException.", e);
		} catch (IllegalAccessException e) {
			log.debug("createBean: Failed to instantiate bean of type [" + beanInformation.getBeanClass().getName()
			        + "] due to IllegalAccessException. Throw BeanTestException.", e);
			throw new BeanTestException("Failed to instantiate bean of type ["
			        + beanInformation.getBeanClass().getName() + "] due to IllegalAccessException.", e);
		}
	}

	/**
	 * Get a factory for the specified property that is of the specified type. <br/>
	 * 
	 * If a Configuration is provided, this is first inspected for a property-specific Factory. <br/>
	 * 
	 * If no Configuration is provided or there is no property-specific Factory in the Configuration, the
	 * FactoryRepository is then searched for a Factory suitable for the type of the property. <br/>
	 * 
	 * If the FactoryRepository does not contain a suitable Factory, an attempt is made to create a Factory for the
	 * type. For example, if the type is an Enum, then a generic Enum Factory will be created for the Enum's constants,
	 * registered in the Factory Repository for future use, and returned from this method. <br/>
	 * 
	 * If ultimately a suitable Factory cannot be found, a BeanTestException detailing the problem is thrown.
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
	 * @throws BeanTestException
	 *             If an unexpected exception occurs when getting the Factory.
	 */
	private Factory<?> getFactory(String propertyName, Class<?> propertyType, Configuration configuration)
	        throws IllegalArgumentException, BeanTestException {
		log.debug("getFactory: entering with propertyName=[" + propertyName + "], propertyType=[" + propertyType
		        + "], configuration=[" + configuration + "].");
		// Validate
		validationHelper.ensureExists(propertyName, "get factory", propertyName);
		if (propertyType == null) {
			throw new IllegalArgumentException("Cannot get factory with null propertyType.");
		}
		// Get factory
		Factory<?> result;
		if ((configuration != null) && (configuration.hasOverrideFactory(propertyName))) {
			// Use the Factory override in the Configuration
			log.debug("getFactory: Configuration has a Factory override for propertyType.");
			result = configuration.getOverrideFactory(propertyName);
		} else if (factoryRepository.hasFactory(propertyType)) {
			// Use the Factory registered in the FactoryRepository
			log.debug("getFactory: FactoryRepository has a Factory registed for propertyType.");
			result = factoryRepository.getFactory(propertyType);
		} else if (propertyType.isEnum()) {
			// Try to create a Factory for the Enum
			log.debug("getFactory: propertyType is an enum. Try to create a generic EnumFactory.");
			EnumFactory enumFactory = new EnumFactory(propertyType, randomNumberGenerator);
			factoryRepository.addFactory(propertyType, enumFactory);
			result = enumFactory;
		} else {
			log.debug("getFactory: Failed to get Factory for property [" + propertyName + "] of type ["
			        + propertyType.getName()
			        + "]. User must register a custom Factory for your custom Data Type. Throw BeanTestException.");
			throw new BeanTestException("Failed to get Factory for property [" + propertyName + "] of type ["
			        + propertyType.getName() + "]. Please register a custom Factory for your custom Data Type.");
		}
		log.debug("getFactory: exiting returning [" + result + "].");
		return result;
	}

	/**
	 * Invoke the specified method on the specified object with the specified parameters.
	 * 
	 * @param bean
	 *            The object the method should be invoked on.
	 * @param method
	 *            The method to invoke.
	 * @param parameter
	 *            The parameters, if any, to pass to the method.
	 * 
	 * @return The object returned from the method invocation, if any; <code>null</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the required parameters are deemed illegal. For example, if they are null.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs when invoking the method.
	 */
	private Object doInvokeMethod(Object bean, Method method, Object... parameters) {
		log.debug("doInvokeMethod: entering with bean=[" + bean + "], method=[" + method + "], parameters=["
		        + parameters + "].");
		validationHelper.ensureExists("bean", "invoke method", bean);
		validationHelper.ensureExists("method", "invoke method", method);
		validationHelper.ensureExists("parameters", "invoke method", parameters);
		try {
			Object result;
			if (parameters.length > 0) {
				result = method.invoke(bean, parameters);
			} else {
				result = method.invoke(bean);
			}
			log.debug("doInvokeMethod: exiting returning [" + result + "].");
			return result;
		} catch (IllegalArgumentException e) {
			log.debug("doInvokeMethod: Failed to invoke method [" + method + "] on bean of type ["
			        + bean.getClass().getName() + "] with parameters [" + parameters
			        + "] due to IllegalArgumentException. Is user passing the correct number "
			        + "of arguments? Throw BeanTestException.", e);
			throw new BeanTestException("Failed to invoke method [" + method + "] on bean of type ["
			        + bean.getClass().getName() + "] with parameters [" + parameters
			        + "] due to IllegalArgumentException. Are you passing the correct number of arguments?", e);
		} catch (IllegalAccessException e) {
			log.debug("doInvokeMethod: Failed to invoke method [" + method + "] on bean of type ["
			        + bean.getClass().getName() + "] with parameters [" + parameters
			        + "] due to IllegalAccessException. Is the method accessible? Throw BeanTestException.", e);
			throw new BeanTestException("Failed to invoke method [" + method + "] on bean of type ["
			        + bean.getClass().getName() + "] with parameters [" + parameters
			        + "] due to IllegalAccessException. Is the method accessible?", e);
		} catch (InvocationTargetException e) {
			log.debug("doInvokeMethod: Failed to invoke method [" + method + "] on bean of type ["
			        + bean.getClass().getName() + "] with parameters [" + parameters
			        + "] due to InvocationTargetException. Throw BeanTestException.", e);
			throw new BeanTestException("Failed to invoke method [" + method + "] on bean of type ["
			        + bean.getClass().getName() + "] with parameters [" + parameters
			        + "] due to InvocationTargetException.", e);
		}
	}

}
