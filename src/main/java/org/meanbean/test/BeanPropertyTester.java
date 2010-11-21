package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * An object that tests a Bean's property methods.
 * 
 * @author Graham Williamson
 */
public class BeanPropertyTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(BeanPropertyTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/**
	 * Test the property specified by the propertyInformation parameter on the specified bean object using the specified
	 * testValue. <br/>
	 * The test is performed by setting the property of the specified bean to the specified testValue via the property
	 * setter method, then getting the value of the property via the property getter method and asserting that the
	 * obtained value matches the testValue. This tests the getter and setter methods of the property.
	 * 
	 * @param bean
	 *            The object the property should be tested on.
	 * @param property
	 *            Information about the property to be tested.
	 * @param testValue
	 *            The value to use when testing the property.
	 * @param equalityTest
	 *            The equality test to perform during testing: Logical (logically equivalence, that is x.equals(y)) or
	 *            Absolute (that is x == y).
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the parameters are deemed illegal. For example, if any are <code>null</code>, if the
	 *             property is not readable and writable, or if the type of the testValue does not match the property
	 *             type.
	 * @throws AssertionError
	 *             If the test fails.
	 * @throws BeanTestException
	 *             If an unexpected exception occurs during testing.
	 */
	public void testProperty(Object bean, PropertyInformation property, Object testValue, EqualityTest equalityTest)
	        throws IllegalArgumentException, AssertionError, BeanTestException {
		log.debug("testProperty: entering with bean=[" + bean + "], property=[" + property + "], testValue=["
		        + testValue + "], equalityTest=[" + equalityTest + "].");
		validationHelper.ensureExists("bean", "test property", bean);
		validationHelper.ensureExists("property", "test property", property);
		validationHelper.ensureExists("testValue", "test property", testValue);
		validationHelper.ensureExists("equalityTest", "test property", equalityTest);
		String propertyName = property.getName();
		if (!property.isReadableWritable()) {
			throw new IllegalArgumentException("Cannot test property [" + propertyName
			        + "] - property must be readable and writable.");
		}
		if (!typesAreCompatible(testValue.getClass(), property.getWriteMethodParameterType())) {
			throw new IllegalArgumentException("Cannot test property [" + propertyName
			        + "] - testValue must be same type as property.");
		}
		try {
			property.getWriteMethod().invoke(bean, testValue);
			Object readMethodOutput = property.getReadMethod().invoke(bean);
			if (!equalityTest.test(testValue, readMethodOutput)) {
				String message = "Property [" + propertyName + "] getter did not return test value. Expected ["
				        + testValue + "] but getter returned [" + readMethodOutput + "].";
				log.info("testProperty: " + message);
				AssertionUtils.fail(message);
			} else {
				log.debug("testProperty: Expected [" + testValue + "] == getter returned [" + readMethodOutput + "].");
			}
		} catch (Exception e) {
			String message = "Failed to test property [" + propertyName + "] due to Exception ["
			        + e.getClass().getName() + "]: [" + e.getMessage() + "].";
			log.error("testProperty: " + message + " Throw BeanTestException.", e);
			throw new BeanTestException(message, e);
		}
		log.debug("testProperty: exiting.");
	}

	/**
	 * Are the specified compatible?
	 * 
	 * @param classA
	 *            A type to compare.
	 * @param superClass
	 *            Another type to compare. If it is possible that one of the types might be a superclass of the other,
	 *            specify that type here.
	 * 
	 * @return <code>true</code> if the specified types are compatible; <code>false</code> otherwise.
	 */
	protected boolean typesAreCompatible(Class<?> classA, Class<?> superClass) {
		if ((!classA.isPrimitive()) && (!superClass.isPrimitive())) {
			return superClass.isAssignableFrom(classA);
		}
		return (classA.getSimpleName().equals(classA.getSimpleName()));
	}
}