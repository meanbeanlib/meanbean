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
 * Concrete EqualsMethodPropertySignificanceVerifier implementation that affords functionality to verify that the equals
 * logic implemented by a type is affected in the expected manner when changes are made to the property values of
 * instances of the type. <br/>
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
 * For the test to function correctly, you must specify all properties that are not used in the equals logic
 * (<quote>insignificant</quote>). <br/>
 * 
 * Use <code>verifyEquals()</code> to test a class that overrides <code>equals()</code>. <br/>
 * 
 * As an example, to verify the equals logic implemented by a class called MyClass do the following:
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
 * The Factory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>. In the above example, there is only one property, name, which is
 * considered by MyClass's equals logic. <br/>
 * 
 * The following example tests the equals logic implemented by a class called MyComplexClass which has two properties:
 * firstName and lastName. Only firstName is considered in the equals logic. Therefore, lastName is specified in the
 * insignificantProperties varargs:
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
	private final ObjectPropertyEqualityConsistentAsserter significantAsserter = new SignificantObjectPropertyEqualityConsistentAsserter();

	/** Asserts that the equality logic is consistent for an insignificant property. */
	private final ObjectPropertyEqualityConsistentAsserter insignificantAsserter = new InsignificantObjectPropertyEqualityConsistentAsserter();

	/**
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
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
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>.
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
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
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
	 *             is <code>null</code>.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	@Override
	public void verifyEqualsMethod(Factory<?> factory, Configuration customConfiguration, String... insignificantProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError {
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
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
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
			Factory<?> propertyFactory = factoryLookupStrategy.getFactory(propertyName,
			        property.getWriteMethodParameterType(), configuration);
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
			String message = "Failed to test property [" + property.getName() + "] due to Exception ["
			        + e.getClass().getName() + "]: [" + e.getMessage() + "].";
			log.error("verifyEqualsMethodForProperty: " + message + " Throw BeanTestException.", e);
			throw new BeanTestException(message, e);
		}
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
}