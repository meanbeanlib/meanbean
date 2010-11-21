package org.meanbean.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.factory.DynamicBeanFactory;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete BeanTester that affords a means of testing JavaBean objects with respect to:
 * 
 * <ul>
 * <li>the correct functioning of the object's public getter and setter methods</li>
 * </ul>
 * 
 * Each property is tested by:
 * 
 * <ol>
 * <li>generating a random test value for the specific property type</li>
 * 
 * <li>invoking the property setter method, passing the generated test value</li>
 * 
 * <li>invoking the property getter method and obtaining the return value</li>
 * 
 * <li>verifying that the value obtained from the getter method matches the value passed to the setter method</li>
 * </ol>
 * 
 * Each property of a type is tested in turn. Each type is tested multiple times to reduce the risk of hard-coded values
 * within a getter or setter matching the random test values generated and the test failing to detect a bug. <br/>
 * 
 * Testing can be configured as follows:
 * 
 * <ul>
 * <li>the number of times each type is tested can be configured</li>
 * 
 * <li>the properties to test can be configured by specifying properties to ignore on a type</li>
 * 
 * <li>custom Factories can be registered to create test values during testing</li>
 * </ul>
 * 
 * See:
 * 
 * <ul>
 * <li><code>setIterations(int)</code> to set the number of times any type is tested</li>
 * 
 * <li><code>addCustomConfiguration(Class<?>,Configuration)</code> to add a custom Configuration across all testing of
 * the specified type</li>
 * 
 * <li><code>testBean(Class<?>,Configuration)</code> to specify a custom Configuration for a single test scenario</li>
 * 
 * <li><code>getFactoryCollection().addFactory(Class<?>,Factory<?>)</code> to add a custom Factory for a type across all
 * testing</li>
 * </ul>
 * 
 * The following example shows how to test a class MyClass:
 * 
 * <pre>
 * BeanTester beanTester = new BasicBeanTester();
 * beanTester.testBean(MyClass.class);
 * </pre>
 * 
 * If the test fails, an AssertionError is thrown. <br/>
 * 
 * To ignore a property (say, lastName) when testing a class:
 * 
 * <pre>
 * BeanTester beanTester = new BasicBeanTester();
 * Configuration configuration = new ConfigurationBuilder().ignoreProperty(&quot;lastName&quot;).build();
 * beanTester.testBean(MyClass.class, configuration);
 * </pre>
 * 
 * @author Graham Williamson
 */
public class BasicBeanTester implements BeanTester {

	/** The number of times each bean is tested, unless a custom Configuration overrides this global setting. */
	private int iterations = BeanTester.TEST_ITERATIONS_PER_BEAN;

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection = new FactoryRepository(randomValueGenerator);

	/** Provides a means of acquiring a suitable Factory. */
	private final FactoryLookupStrategy factoryLookupStrategy = new BasicFactoryLookupStrategy(factoryCollection,
	        randomValueGenerator);

	/** Custom Configurations that override standard testing behaviour on a per-type basis across all tests. */
	private final Map<Class<?>, Configuration> customConfigurations = Collections
	        .synchronizedMap(new HashMap<Class<?>, Configuration>());

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	/** Object that tests the getters and setters of a Bean's property. */
	private final BeanPropertyTester beanPropertyTester = new BeanPropertyTester();

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(BasicBeanTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/**
	 * The collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	@Override
	public FactoryCollection getFactoryCollection() {
		return factoryCollection;
	}

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	@Override
	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
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
	 * Test the type specified by the beanClass parameter. <br />
	 * 
	 * Testing will test each publicly readable and writable property of the specified beanClass to ensure that the
	 * getters and setters function correctly. <br />
	 * 
	 * The test is performed repeatedly using random data for each scenario to prevent salient getter/setter failures. <br />
	 * 
	 * When a test is failed, an AssertionError is thrown.
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass is deemed illegal. For example, if it is null.
	 * @throws AssertionError
	 *             If the bean fails the test.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	@Override
	public void testBean(Class<?> beanClass) throws IllegalArgumentException, AssertionError, BeanTestException {
		log.debug("testBean: entering with beanClass=[" + beanClass + "].");
		validationHelper.ensureExists("beanClass", "test bean", beanClass);
		Configuration customConfiguration = null;
		if (hasCustomConfiguration(beanClass)) {
			customConfiguration = getCustomConfiguration(beanClass);
		}
		testBean(beanClass, customConfiguration);
		log.debug("testBean: exiting.");
	}

	/**
	 * Test the type specified by the beanClass parameter, using the custom Configuration provided as an override to any
	 * global configuration settings. <br />
	 * 
	 * Testing will test each publicly readable and writable property of the specified beanClass to ensure that the
	 * getters and setters function correctly. <br />
	 * 
	 * The test is performed repeatedly using random data for each scenario to prevent salient getter/setter failures. <br />
	 * 
	 * When a test is failed, an AssertionError is thrown.
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * @param configuration
	 *            The custom Configuration to be used when testing the specified beanClass type. This Configuration is
	 *            only used for this individual test and will not be retained for future testing of this or any other
	 *            type. To register a custom Configuration across multiple tests, use
	 *            <code>addCustomConfiguration()</code>. If no custom Configuration is required, pass <code>null</code>
	 *            or use <code>testBean(Class<?>)</code> instead.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass is deemed illegal. For example, if it is null.
	 * @throws AssertionError
	 *             If the bean fails the test.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	@Override
	public void testBean(Class<?> beanClass, Configuration customConfiguration) throws IllegalArgumentException,
	        AssertionError, BeanTestException {
		log.debug("testBean: entering with beanClass=[" + beanClass + "], customConfiguration=[" + customConfiguration
		        + "].");
		validationHelper.ensureExists("beanClass", "test bean", beanClass);
		// Override the standard number of iterations if need be
		int iterations = this.iterations;
		if ((customConfiguration != null) && (customConfiguration.hasIterationsOverride())) {
			iterations = customConfiguration.getIterations();
		}
		// Get all information about a potential JavaBean class
		BeanInformation beanInformation = beanInformationFactory.create(beanClass);
		// Test the JavaBean 'iterations' times
		for (int idx = 0; idx < iterations; idx++) {
			log.debug("testBean: Iteration [" + idx + "].");
			testBean(beanInformation, customConfiguration);
		}
		log.debug("testBean: exiting.");
	}

	/**
	 * Test the type specified by the beanInformation parameter using the specified Configuration. <br />
	 * 
	 * Testing will test each publicly readable and writable property of the specified beanClass to ensure that the
	 * getters and setters function correctly. <br />
	 * 
	 * The test is performed repeatedly using random data for each scenario to prevent salient getter/setter failures. <br />
	 * 
	 * When a test is failed, an AssertionError is thrown.
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * @param configuration
	 *            The custom Configuration to be used when testing the specified beanClass type. If no custom
	 *            Configuration is required, pass <code>null</code> or use <code>testBean(Class<?>)</code> instead.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanInformation is deemed illegal. For example, if it is null.
	 * @throws AssertionError
	 *             If the bean fails the test.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	protected void testBean(BeanInformation beanInformation, Configuration configuration)
	        throws IllegalArgumentException, AssertionError, BeanTestException {
		log.debug("testBean: entering with beanInformation=[" + beanInformation + "], configuration=[" + configuration
		        + "].");
		validationHelper.ensureExists("beanInformation", "test bean", beanInformation);
		// Get all properties of the bean
		Collection<PropertyInformation> properties = beanInformation.getProperties();
		// Get just the properties of the bean that are readable and writable
		Collection<PropertyInformation> readableWritableProperties = PropertyInformationFilter.filter(properties,
		        PropertyVisibility.READABLE_WRITABLE);
		// Instantiate
		Object bean = null;
		try {
			Factory<Object> beanFactory = new DynamicBeanFactory(beanInformation);
			bean = beanFactory.create();
		} catch (Exception e) {
			String message = "Cannot test bean [" + beanInformation.getBeanClass().getName()
			        + "]. Failed to instantiate an instance of the bean.";
			log.error("testBean: " + message + " Throw BeanTestException.", e);
			throw new BeanTestException(message, e);
		}
		// Test each property
		for (PropertyInformation property : readableWritableProperties) {
			// Skip testing any 'ignored' properties
			if ((configuration == null) || (!configuration.isIgnoredProperty(property.getName()))) {
				EqualityTest equalityTest = EqualityTest.LOGICAL;
				Object testValue = null;
				try {
					Factory<?> valueFactory = factoryLookupStrategy.getFactory(property.getName(),
					        property.getWriteMethodParameterType(), configuration);
					testValue = valueFactory.create();
					if (valueFactory instanceof DynamicBeanFactory) {
						equalityTest = EqualityTest.ABSOLUTE;
					}
				} catch (Exception e) {
					String message = "Cannot test bean [" + beanInformation.getBeanClass().getName()
					        + "]. Failed to instantiate a test value for property [" + property.getName() + "].";
					log.error("testBean: " + message + " Throw BeanTestException.", e);
					throw new BeanTestException(message, e);
				}
				beanPropertyTester.testProperty(bean, property, testValue, equalityTest);
			}
		}
		log.debug("testBean: exiting.");
	}
}