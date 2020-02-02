package org.meanbean.test;

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.factories.BasicNewObjectInstanceFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * Concrete BeanTester that affords a means of testing JavaBean objects with respect to:
 * </p>
 * 
 * <ul>
 * <li>the correct functioning of the object's public getter and setter methods</li>
 * </ul>
 * 
 * <p>
 * Each property is tested by:
 * </p>
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
 * <p>
 * Each property of a type is tested in turn. Each type is tested multiple times to reduce the risk of hard-coded values
 * within a getter or setter matching the random test values generated and the test failing to detect a bug. <br/>
 * </p>
 * 
 * <p>
 * Testing can be configured as follows:
 * </p>
 * 
 * <ul>
 * <li>the number of times each type is tested can be configured</li>
 * 
 * <li>the properties to test can be configured by specifying properties to ignore on a type</li>
 * 
 * <li>custom Factories can be registered to create test values during testing</li>
 * </ul>
 * 
 * <p>
 * See:
 * </p>
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
 * <p>
 * The following example shows how to test a class MyClass:
 * </p>
 * 
 * <pre>
 * BeanTester beanTester = new BeanTester();
 * beanTester.testBean(MyClass.class);
 * </pre>
 * 
 * <p>
 * If the test fails, an AssertionError is thrown. <br/>
 * </p>
 * 
 * <p>
 * To ignore a property (say, lastName) when testing a class:
 * </p>
 * 
 * <pre>
 * BeanTester beanTester = new BeanTester();
 * Configuration configuration = new ConfigurationBuilder().ignoreProperty(&quot;lastName&quot;).build();
 * beanTester.testBean(MyClass.class, configuration);
 * </pre>
 * 
 * @author Graham Williamson
 */
public class BeanTester {

	/** Default number of times a bean should be tested. */
	public static final int TEST_ITERATIONS_PER_BEAN = 100;

    /** Logging mechanism. */
    private static final $Logger logger = $LoggerFactory.getLogger(BeanTester.class);

	/** The number of times each bean is tested, unless a custom Configuration overrides this global setting. */
	private int iterations = TEST_ITERATIONS_PER_BEAN;

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator;

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection;

	/** Provides a means of acquiring a suitable Factory. */
	private final FactoryLookupStrategy factoryLookupStrategy;

	/** Custom Configurations that override standard testing behaviour on a per-type basis across all tests. */
	private final Map<Class<?>, Configuration> customConfigurations = new ConcurrentHashMap<>();

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory;

	/** Object that tests the getters and setters of a Bean's property. */
	private final BeanPropertyTester beanPropertyTester;

	public BeanTester() {
		this(TEST_ITERATIONS_PER_BEAN, 
				RandomValueGenerator.getInstance(),
				FactoryCollection.getInstance(),
				FactoryLookupStrategy.getInstance(),
				BeanInformationFactory.getInstance(),
				new BeanPropertyTester());
	}

	BeanTester(int iterations, RandomValueGenerator randomValueGenerator, FactoryCollection factoryCollection,
			FactoryLookupStrategy factoryLookupStrategy, BeanInformationFactory beanInformationFactory,
			BeanPropertyTester beanPropertyTester) {
		this.iterations = iterations;
		this.randomValueGenerator = randomValueGenerator;
		this.factoryCollection = factoryCollection;
		this.factoryLookupStrategy = factoryLookupStrategy;
		this.beanInformationFactory = beanInformationFactory;
		this.beanPropertyTester = beanPropertyTester;

		if (iterations < 1) {
			throw new IllegalArgumentException("Iterations must be at least 1.");
		}
	}

	/**
	 * The collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	public FactoryCollection getFactoryCollection() {
		return factoryCollection;
	}

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	/**
	 * <p>
	 * Get the number of times each bean should be tested. <br/>
	 * </p>
	 * 
	 * <p>
	 * Note: A custom Configuration can override this global setting.
	 * </p>
	 * 
	 * @return The number of times each bean should be tested. This value will be at least 1.
	 */
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
	public void addCustomConfiguration(Class<?> beanClass, Configuration configuration) throws IllegalArgumentException {
		ValidationHelper.ensureExists("beanClass", "add custom configuration", beanClass);
		ValidationHelper.ensureExists("configuration", "add custom configuration", configuration);
		customConfigurations.put(beanClass, configuration);
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
		ValidationHelper.ensureExists("beanClass", "check for custom configuration", beanClass);
		boolean result = customConfigurations.containsKey(beanClass);
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
		ValidationHelper.ensureExists("beanClass", "get custom configuration", beanClass);
		Configuration result = customConfigurations.get(beanClass);
		return result;
	}

	/**
	 * <p>
	 * Test the type specified by the beanClass parameter.
	 * </p>
	 * 
	 * <p>
	 * Testing will test each publicly readable and writable property of the specified beanClass to ensure that the
	 * getters and setters function correctly.
	 * </p>
	 * 
	 * <p>
	 * The test is performed repeatedly using random data for each scenario to prevent salient getter/setter failures.
	 * </p>
	 * 
	 * <p>
	 * When a test is failed, an AssertionError is thrown.
	 * </p>
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
	public void testBean(Class<?> beanClass) throws IllegalArgumentException, AssertionError, BeanTestException {
		ValidationHelper.ensureExists("beanClass", "test bean", beanClass);
		Configuration customConfiguration = null;
		if (hasCustomConfiguration(beanClass)) {
			customConfiguration = getCustomConfiguration(beanClass);
		}
		testBean(beanClass, customConfiguration);
	}

	/**
	 * <p>
	 * Test the type specified by the beanClass parameter, using the custom Configuration provided as an override to any
	 * global configuration settings. <br />
	 * </p>
	 * 
	 * <p>
	 * Testing will test each publicly readable and writable property of the specified beanClass to ensure that the
	 * getters and setters function correctly. <br />
	 * </p>
	 * 
	 * <p>
	 * The test is performed repeatedly using random data for each scenario to prevent salient getter/setter failures. <br />
	 * </p>
	 * 
	 * <p>
	 * When a test is failed, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param beanClass
	 *            The type to be tested.
	 * @param customConfiguration
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
	public void testBean(Class<?> beanClass, Configuration customConfiguration) throws IllegalArgumentException,
	        AssertionError, BeanTestException {
		ValidationHelper.ensureExists("beanClass", "test bean", beanClass);
		// Override the standard number of iterations if need be
		int iterations = this.iterations;
		if ((customConfiguration != null) && (customConfiguration.hasIterationsOverride())) {
			iterations = customConfiguration.getIterations();
		}
		// Get all information about a potential JavaBean class
		BeanInformation beanInformation = beanInformationFactory.create(beanClass);
		// Test the JavaBean 'iterations' times
		for (int idx = 0; idx < iterations; idx++) {
			testBean(beanInformation, customConfiguration);
		}
	}

	/**
	 * <p>
	 * Test the type specified by the beanInformation parameter using the specified Configuration. <br />
	 * </p>
	 * 
	 * <p>
	 * Testing will test each publicly readable and writable property of the specified beanClass to ensure that the
	 * getters and setters function correctly. <br />
	 * </p>
	 * 
	 * <p>
	 * The test is performed repeatedly using random data for each scenario to prevent salient getter/setter failures. <br />
	 * </p>
	 * 
	 * <p>
	 * When a test is failed, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param beanInformation
	 *            Information about the type to be tested.
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
		ValidationHelper.ensureExists("beanInformation", "test bean", beanInformation);
		// Get all properties of the bean
		Collection<PropertyInformation> properties = beanInformation.getProperties();
		// Get just the properties of the bean that are readable and writable
		Collection<PropertyInformation> readableWritableProperties =
		        PropertyInformationFilter.filter(properties, PropertyVisibility.READABLE_WRITABLE);
		// Instantiate
		BasicNewObjectInstanceFactory beanFactory = new BasicNewObjectInstanceFactory(beanInformation.getBeanClass());
		Object bean;
		try {
			bean = beanFactory.create();
		} catch (Exception e) {
			String message =
			        "Cannot test bean [" + beanInformation.getBeanClass().getName()
			                + "]. Failed to instantiate an instance of the bean.";
			logger.error("testBean:{} Throw BeanTestException.", message, e);
			throw new BeanTestException(message, e);
		}
		// Test each property
		for (PropertyInformation property : readableWritableProperties) {
			// Skip testing any 'ignored' properties
			if ((configuration == null) || (!configuration.isIgnoredProperty(property.getName()))) {
				EqualityTest equalityTest = EqualityTest.LOGICAL;
				Object testValue = null;
				try {
					Factory<?> valueFactory =
					        factoryLookupStrategy.getFactory(beanInformation, property.getName(),
					                property.getWriteMethodParameterType(), configuration);
					testValue = valueFactory.create();
					if (valueFactory instanceof BasicNewObjectInstanceFactory) {
						equalityTest = EqualityTest.ABSOLUTE;
					}
				} catch (Exception e) {
					String message =
					        "Cannot test bean [" + beanInformation.getBeanClass().getName()
					                + "]. Failed to instantiate a test value for property [" + property.getName()
					                + "].";
					logger.error("testBean:{} Throw BeanTestException.", message, e);
					throw new BeanTestException(message, e);
				}
				beanPropertyTester.testProperty(bean, property, testValue, equalityTest);
			}
		}
	}
}