package org.meanbean.test;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.util.RandomValueGeneratorProvider;

/**
 * Defines an object that can test JavaBean objects. <br/>
 * 
 * A means of customising testing on a global and per-type basis is also defined.
 * 
 * @author Graham Williamson
 */
public interface BeanTester extends RandomValueGeneratorProvider {

	/** Default number of times a bean should be tested. */
	static final int TEST_ITERATIONS_PER_BEAN = 100;

	/**
	 * The collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	FactoryCollection getFactoryCollection();

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
	void setIterations(int iterations) throws IllegalArgumentException;

	/**
	 * Get the number of times each bean should be tested. <br/>
	 * 
	 * Note: A custom Configuration can override this global setting.
	 * 
	 * @return The number of times each bean should be tested. This value will be at least 1.
	 */
	int getIterations();

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
	void addCustomConfiguration(Class<?> beanClass, Configuration configuration) throws IllegalArgumentException;

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
	void testBean(Class<?> beanClass) throws IllegalArgumentException, AssertionError, BeanTestException;

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
	void testBean(Class<?> beanClass, Configuration customConfiguration) throws IllegalArgumentException,
	        AssertionError, BeanTestException;
}