package org.meanbean.test;

import java.util.ArrayList;
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
import org.meanbean.bean.util.PropertyInformationFilter;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.factories.util.BasicFactoryLookupStrategy;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.StringUtils;
import org.meanbean.util.ValidationHelper;

/**
 * <p>
 * Concrete EqualsMethodPropertySignificanceVerifier implementation that affords functionality to verify that the equals
 * logic implemented by a type is affected in the expected manner when changes are made to the property values of
 * instances of the type. <br/>
 * </p>
 * 
 * <p>
 * That is:
 * </p>
 * 
 * <ul>
 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in the
 * equality logic</li>
 * 
 * <li>the equality of an object should be affected by properties that are changed and are considered in the equality
 * logic</li>
 * </ul>
 * 
 * <p>
 * To do this, instances of the type are created using a specified factory, their properties are manipulated
 * individually and the equality is reassessed. <br/>
 * </p>
 * 
 * <p>
 * For the test to function correctly, you must specify all properties that are not used in the equals logic
 * (<quote>insignificant</quote>). <br/>
 * </p>
 * 
 * <p>
 * Use <code>verifyEquals()</code> to test a class that overrides <code>equals()</code>. <br/>
 * </p>
 * 
 * <p>
 * As an example, to verify the equals logic implemented by a class called MyClass do the following:
 * </p>
 * 
 * <pre>
 * EqualsMethodPropertySignificanceVerifier verifier = new PropertyBasedEqualsMethodPropertySignificanceVerifier();
 * verifier.verifyEqualsMethod(new Factory<MyClass>() {
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
 * <p>
 * The Factory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>. In the above example, there is only one property, name, which is
 * considered by MyClass's equals logic. <br/>
 * </p>
 * 
 * <p>
 * The following example tests the equals logic implemented by a class called MyComplexClass which has two properties:
 * firstName and lastName. Only firstName is considered in the equals logic. Therefore, lastName is specified in the
 * insignificantProperties varargs:
 * </p>
 * 
 * <pre>
 * EqualsMethodPropertySignificanceVerifier verifier = new PropertyBasedEqualsMethodPropertySignificanceVerifier();
 * verifier.verifyEqualsMethod(new Factory<MyComplexClass>() {
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
class PropertyBasedEqualsMethodPropertySignificanceVerifier implements EqualsMethodPropertySignificanceVerifier {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(PropertyBasedEqualsMethodPropertySignificanceVerifier.class);

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

	/** Asserts that the equality logic is consistent for a significant property. */
	private final ObjectPropertyEqualityConsistentAsserter significantAsserter =
	        new SignificantObjectPropertyEqualityConsistentAsserter();

	/** Asserts that the equality logic is consistent for an insignificant property. */
	private final ObjectPropertyEqualityConsistentAsserter insignificantAsserter =
	        new InsignificantObjectPropertyEqualityConsistentAsserter();

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the property values of instances of the type. <br/>
	 * </p>
	 * 
	 * <p>
	 * That is:
	 * </p>
	 * 
	 * <ul>
	 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in
	 * the equality logic</li>
	 * 
	 * <li>the equality of an object should be affected by properties that are changed and are considered in the
	 * equality logic</li>
	 * </ul>
	 * 
	 * <p>
	 * To do this, instances of the type are created using the specified factory, their properties are manipulated
	 * individually and the equality is reassessed. <br/>
	 * </p>
	 * 
	 * <p>
	 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test the equals
	 *            logic. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>. Also, if any of the specified insignificantProperties do not exist on the class
	 *             under test.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	@Override
	public void verifyEqualsMethod(Factory<?> factory, String... insignificantProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError {
		verifyEqualsMethod(factory, null, insignificantProperties);
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the property values of instances of the type. <br/>
	 * </p>
	 * 
	 * <p>
	 * That is:
	 * </p>
	 * 
	 * <ul>
	 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in
	 * the equality logic</li>
	 * 
	 * <li>the equality of an object should be affected by properties that are changed and are considered in the
	 * equality logic</li>
	 * </ul>
	 * 
	 * <p>
	 * To do this, instances of the type are created using the specified factory, their properties are manipulated
	 * individually and the equality is reassessed. <br/>
	 * </p>
	 * 
	 * <p>
	 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test the equals
	 *            logic. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * @param customConfiguration
	 *            A custom Configuration to be used when testing to ignore the testing of named properties or use a
	 *            custom test data Factory when testing a named property. This Configuration is only used for this
	 *            individual test and will not be retained for future testing of this or any other type. If no custom
	 *            Configuration is required, pass <code>null</code> or use
	 *            <code>verifyEqualsMethod(Factory<?>,String...)</code> instead.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>. Also, if any of the specified insignificantProperties do not exist on the class
	 *             under test.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	@Override
	public void verifyEqualsMethod(Factory<?> factory, Configuration customConfiguration,
	        String... insignificantProperties) throws IllegalArgumentException, BeanInformationException,
	        BeanTestException, AssertionError {
		log.debug("verifyEqualsMethod: Entering with factory=[" + factory + "], configuration=[" + customConfiguration
		        + "] and insignificantProperties=[" + insignificantProperties + "].");
		validationHelper.ensureExists("factory", "test equals", factory);
		validationHelper.ensureExists("insignificantProperties", "test equals", insignificantProperties);
		List<String> insignificantPropertyNames = Arrays.asList(insignificantProperties);
		Object prototype = factory.create();
		log.debug("verifyEqualsMethod: Created object prototype=[" + prototype + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals", prototype);
		BeanInformation beanInformation = beanInformationFactory.create(prototype.getClass());
		log.debug("verifyEqualsMethod: Acquired beanInformation=[" + beanInformation + "].");
		ensureInsignificantPropertiesExist(beanInformation, insignificantPropertyNames);
		Collection<PropertyInformation> properties = beanInformation.getProperties();
		properties = PropertyInformationFilter.filter(properties, PropertyVisibility.READABLE_WRITABLE);
		for (PropertyInformation property : properties) {
			if (customConfiguration == null || !customConfiguration.isIgnoredProperty(property.getName())) {
				verifyEqualsMethodForProperty(factory, customConfiguration, property,
				        !insignificantPropertyNames.contains(property.getName()));
			} else {
				log.debug("verifyEqualsMethod: Ignoring property=[" + property.getName() + "].");
			}
		}
	}

	/**
	 * Ensure that all of the specified insignificant properties exist on the specified bean. If an insignificant
	 * property is specified that does not exist on the bean, an <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param beanInformation
	 *            Information about the bean on which all of the insignificant properties should exist.
	 * @param insignificantProperties
	 *            The names of the insignificant properties that should exist on the bean.
	 * 
	 * @throws IllegalArgumentException
	 *             If an insignificant property is specified that does not exist on the specified bean.
	 */
	protected void ensureInsignificantPropertiesExist(BeanInformation beanInformation,
	        List<String> insignificantProperties) throws IllegalArgumentException {
		log.debug("ensureInsignificantPropertiesExist: Entering with beanInformation=[" + beanInformation
		        + "] and insignificantProperties=[" + insignificantProperties + "].");
		List<String> unrecognisedPropertyNames = new ArrayList<String>(insignificantProperties);
		unrecognisedPropertyNames.removeAll(beanInformation.getPropertyNames());
		if (!unrecognisedPropertyNames.isEmpty()) {
			String message =
			        "Insignificant properties [" + StringUtils.join(unrecognisedPropertyNames, ",")
			                + "] do not exist on " + beanInformation.getBeanClass().getName() + ".";
			log.debug("ensureInsignificantPropertiesExist: " + message + " Throw IllegalArgumentException.");
			throw new IllegalArgumentException(message);
		}
		log.debug("ensureInsignificantPropertiesExist: Exiting.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the value of the specified property. <br/>
	 * </p>
	 * 
	 * <p>
	 * That is:
	 * </p>
	 * 
	 * <ul>
	 * <li>the equality of an object should not be affected by properties that are changed, but are not considered in
	 * the equality logic</li>
	 * 
	 * <li>the equality of an object should be affected by properties that are changed and are considered in the
	 * equality logic</li>
	 * </ul>
	 * 
	 * <p>
	 * To do this, instances of the type are created using the specified factory, the specified property is manipulated
	 * and the equality is reassessed. <br/>
	 * </p>
	 * 
	 * <p>
	 * For the test to function correctly, you must specify whether the property is used in the equals logic. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
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
	 * @param significant
	 *            Set to <code>true</code> if the property is used when deciding whether objects are logically
	 *            equivalent; set to <code>false</code> if the property is not used when deciding whether objects are
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
	protected void verifyEqualsMethodForProperty(Factory<?> factory, Configuration configuration,
	        PropertyInformation property, boolean significant) throws IllegalArgumentException,
	        BeanInformationException, BeanTestException, AssertionError {
		log.debug("verifyEqualsMethodForProperty: Entering with factory=[" + factory + "], configuration=["
		        + configuration + "], property=[" + property + "] and significant=[" + significant + "].");
		String propertyName = property.getName();
		log.debug("verifyEqualsMethodForProperty: Test for property=[" + propertyName + "].");
		Object originalObj = factory.create();
		Object modifiedObj = factory.create();
		log.debug("verifyEqualsMethodForProperty: Created objects x=[" + originalObj + "] and y=[" + modifiedObj + "].");
		if (!originalObj.equals(modifiedObj)) {
			String message = "Cannot test equals if factory does not create logically equivalent objects.";
			log.debug("verifyEqualsMethodForProperty: " + message + " Throw IllegalArgumentException.");
			throw new IllegalArgumentException(message);
		}
		try {
			Object xOriginalValue = property.getReadMethod().invoke(originalObj);
			Object originalVal = property.getReadMethod().invoke(modifiedObj);
			validationHelper.ensureExists("factory-created object." + propertyName, "test equals", xOriginalValue);
			validationHelper.ensureExists("factory-created object." + propertyName, "test equals", originalVal);
			if (!originalVal.equals(xOriginalValue)) {
				String message = "Cannot test equals if factory does not create objects with same property values.";
				log.debug("verifyEqualsMethodForProperty: " + message + " Throw IllegalArgumentException.");
				throw new IllegalArgumentException(message);
			}
			Factory<?> propertyFactory =
			        factoryLookupStrategy.getFactory(propertyName, property.getWriteMethodParameterType(),
			                configuration);
			Object newVal = propertyFactory.create();
			log.debug("verifyEqualsMethodForProperty: Original property value=[" + originalVal
			        + "]; new property value=[" + newVal + "].");
			property.getWriteMethod().invoke(modifiedObj, newVal);
			if (significant) {
				significantAsserter.assertConsistent(propertyName, originalObj, modifiedObj, originalVal, newVal);
			} else {
				insignificantAsserter.assertConsistent(propertyName, originalObj, modifiedObj, originalVal, newVal);
			}
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) e; // re-throw without wrapping
			}
			String message =
			        "Failed to test property [" + property.getName() + "] due to Exception [" + e.getClass().getName()
			                + "]: [" + e.getMessage() + "].";
			log.error("verifyEqualsMethodForProperty: " + message + " Throw BeanTestException.", e);
			throw new BeanTestException(message, e);
		}
	}

	/**
	 * Get a RandomValueGenerator.
	 * 
	 * @return A RandomValueGenerator.
	 */
	@Override
	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	/**
	 * Get the collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	@Override
	public FactoryCollection getFactoryCollection() {
		return factoryCollection;
	}

	/**
	 * Get the FactoryLookupStrategy, which provides a means of acquiring Factories.
	 * 
	 * @return The factory lookup strategy.
	 */
	@Override
	public FactoryLookupStrategy getFactoryLookupStrategy() {
		return factoryLookupStrategy;
	}
}