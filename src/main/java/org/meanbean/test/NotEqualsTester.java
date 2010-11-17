package org.meanbean.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationException;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.lang.Factory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Class that affords functionality to test the equals logic implemented by a type is affected in the expected manner
 * when changes are made to the property values of instances of the type. <br/>
 * 
 * That is:
 * 
 * <ul>
 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in the
 * equality logic</li>
 * 
 * <li>the equality of an object should be affected by properties that are changed and are considered in the equality
 * logic</li>
 * </ul>
 * 
 * To do this, instances of the type are created using a specified factory, their properties are manipulated
 * individually and the equality is reassessed. <br/>
 * 
 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br/>
 * 
 * Use <code>testEquals()</code> to test a class that overrides <code>equals()</code>. <br/>
 * 
 * As an example, to test the equals logic implemented by a class called MyClass do the following:
 * 
 * <pre>
 * EqualsTester equalsTester = new EqualsTester();
 * equalsTester.testEquals(new Factory<MyClass>() {
 *    @Override
 *    public MyClass create() {
 *       MyClass() result = new MyClass();
 *       // initialize result...
 *       result.setName("TEST_NAME");
 *       return result;
 *    }
 * });
 * </pre>
 * 
 * The Factory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>. In the above example, there is only one property, name, which is
 * considered by MyClass's equals logic. <br/>
 * 
 * The following example tests the equals logic implemented by a class called MyComplexClass which has two properties:
 * firstName and lastName. Only firstName is considered in the equals logic. Therefore, we lastName is specified in the
 * unusedProperties varargs:
 * 
 * <pre>
 * EqualsTester equalsTester = new EqualsTester();
 * equalsTester.testEquals(new Factory<MyComplexClass>() {
 *    @Override
 *    public MyComplexClass create() {
 *       MyComplexClass() result = new MyComplexClass();
 *       // initialize result...
 *       result.setFirstName("TEST_FIRST_NAME");
 *       result.setLastName("TEST_LAST_NAME");
 *       return result;
 *    }
 * }, "lastName");
 * </pre>
 * 
 * @author Graham Williamson
 */
public class NotEqualsTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(NotEqualsTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection = new FactoryRepository(randomValueGenerator);

	/** Provides a means of acquiring a suitable Factory. */
	private final FactoryLookupStrategy factoryLookupStrategy = new BasicFactoryLookupStrategy(factoryCollection,
	        randomValueGenerator);

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the property values of instances of the type. <br/>
	 * 
	 * That is:
	 * 
	 * <ul>
	 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in
	 * the equality logic</li>
	 * 
	 * <li>the equality of an object should be affected by properties that are changed and are considered in the
	 * equality logic</li>
	 * </ul>
	 * 
	 * To do this, instances of the type are created using the specified factory, their properties are manipulated
	 * individually and the equality is reassessed. <br/>
	 * 
	 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test the equals
	 *            logic. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * @param unusedProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or unusedProperties are deemed illegal. For example, if either is
	 *             <code>null</code>.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEquals(Factory<?> factory, String... unusedProperties) throws IllegalArgumentException,
	        BeanInformationException, BeanTestException, AssertionError {
		testEquals(factory, null, unusedProperties);
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the property values of instances of the type. <br/>
	 * 
	 * That is:
	 * 
	 * <ul>
	 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in
	 * the equality logic</li>
	 * 
	 * <li>the equality of an object should be affected by properties that are changed and are considered in the
	 * equality logic</li>
	 * </ul>
	 * 
	 * To do this, instances of the type are created using the specified factory, their properties are manipulated
	 * individually and the equality is reassessed. <br/>
	 * 
	 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test the equals
	 *            logic. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * @param configuration
	 *            A custom Configuration to be used when testing to ignore the testing of named properties or use a
	 *            custom test data Factory when testing a named property. This Configuration is only used for this
	 *            individual test and will not be retained for future testing of this or any other type. If no custom
	 *            Configuration is required, pass <code>null</code> or use <code>testEquals(Factory<?>,String...)</code>
	 *            instead.
	 * @param unusedProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or unusedProperties are deemed illegal. For example, if either is
	 *             <code>null</code>.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEquals(Factory<?> factory, Configuration configuration, String... unusedProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError {
		log.debug("testEquals: Entering with factory=[" + factory + "], configuration=[" + configuration
		        + "] and unusedProperties=[" + unusedProperties + "].");
		validationHelper.ensureExists("factory", "test equals", factory);
		validationHelper.ensureExists("unusedProperties", "test equals", unusedProperties);
		List<String> unusedPropertyNames = Arrays.asList(unusedProperties);
		Object prototype = factory.create();
		log.debug("testEquals: Created object prototype=[" + prototype + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals", prototype);
		BeanInformation beanInformation = beanInformationFactory.create(prototype.getClass());
		log.debug("testEquals: Acquired beanInformation=[" + beanInformation + "].");
		Collection<PropertyInformation> properties = beanInformation.getProperties();
		for (PropertyInformation property : properties) {
			if (configuration == null || !configuration.isIgnoredProperty(property.getName())) {
				testEqualsForProperty(factory, configuration, property,
				        unusedPropertyNames.contains(property.getName()));
			}
		}
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the value of the specified property. <br/>
	 * 
	 * That is:
	 * 
	 * <ul>
	 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in
	 * the equality logic</li>
	 * 
	 * <li>the equality of an object should be affected by properties that are changed and are considered in the
	 * equality logic</li>
	 * </ul>
	 * 
	 * To do this, instances of the type are created using the specified factory, the specified property is manipulated
	 * and the equality is reassessed. <br/>
	 * 
	 * For the test to function correctly, you must specify whether the property is used in the equals logic. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test the equals
	 *            logic. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * @param configuration
	 *            A custom Configuration to be used when testing to ignore the testing of named properties or use a
	 *            custom test data Factory when testing a named property. This Configuration is only used for this
	 *            individual test and will not be retained for future testing of this or any other type. If no custom
	 *            Configuration is required, pass <code>null</code>.
	 * @param property
	 *            The property to test.
	 * @param isUnused
	 *            Set to <code>true</code> if the property is not used when deciding whether objects are logically
	 *            equivalent; set to <code>false</code> if the property is used when deciding whether objects are
	 *            logically equivalent.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the parameters are deemed illegal. For example, if any are <code>null</code> (except
	 *             configuration, which can be <code>null</code>).
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the property, such as an inability to read or write the property.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	protected void testEqualsForProperty(Factory<?> factory, Configuration configuration, PropertyInformation property,
	        boolean isUnused) throws IllegalArgumentException, BeanInformationException, BeanTestException,
	        AssertionError {
		String propertyName = property.getName();
		log.debug("testEqualsForProperty: Test for property=[" + propertyName + "].");
		Object x = factory.create();
		Object y = factory.create();
		log.debug("testEqualsForProperty: Created objects x=[" + x + "] and y=[" + y + "].");
		if (!x.equals(y)) {
			String message = "Cannot test equals if factory does not create logically equivalent objects.";
			log.debug("testEqualsForProperty: " + message + " Throw IllegalArgumentException.");
			throw new IllegalArgumentException(message);
		}
		try {
			Object xOriginalValue = property.getReadMethod().invoke(x);
			Object originalValue = property.getReadMethod().invoke(y);
			validationHelper.ensureExists("factory-created object." + propertyName, "test equals", xOriginalValue);
			validationHelper.ensureExists("factory-created object." + propertyName, "test equals", originalValue);
			if (!originalValue.equals(xOriginalValue)) {
				String message = "Cannot test equals if factory does not create objects with same property values.";
				log.debug("testEqualsForProperty: " + message + " Throw IllegalArgumentException.");
				throw new IllegalArgumentException(message);
			}
			Factory<?> propertyFactory = factoryLookupStrategy.getFactory(propertyName,
			        property.getWriteMethodParameterType(), configuration);
			Object newValue = propertyFactory.create();
			log.debug("testEqualsForProperty: Original property value=[" + originalValue + "]; new property value=["
			        + newValue + "].");
			property.getWriteMethod().invoke(y, newValue);

			if (isUnused && !x.equals(y)) {// equality shouldn't have changed but did
				String message = "objects that differ due to (irrelevant) property [" + propertyName
				        + "] where considered unequal. (x." + propertyName + "=[" + originalValue + "] vs y."
				        + propertyName + "=[" + newValue + "]).";
				log.debug("testEquals: " + message);
				AssertionUtils.fail(message);
			}
			if (!isUnused && x.equals(y)) {// equality should have changed but didn't
				String message = "objects that differ due to property [" + propertyName
				        + "] where considered equal. (x." + propertyName + "=[" + originalValue + "] vs y."
				        + propertyName + "=[" + newValue + "]).";
				log.debug("testEqualsForProperty: " + message);
				AssertionUtils.fail(message);
			}
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) e; // re-throw without wrapping
			}
			String message = "Failed to test property [" + property.getName() + "] due to Exception ["
			        + e.getClass().getName() + "]: [" + e.getMessage() + "].";
			log.error("testEqualsForProperty: " + message + " Throw BeanTestException.", e);
			throw new BeanTestException(message, e);
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
}