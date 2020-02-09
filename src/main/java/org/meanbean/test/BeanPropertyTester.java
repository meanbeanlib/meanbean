/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

package org.meanbean.test;

import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.ValidationHelper;

import java.lang.reflect.Type;

/**
 * An object that tests a Bean's property methods.
 * 
 * @author Graham Williamson
 */
public class BeanPropertyTester {

	/**
	 * <p>
	 * Test the property specified by the propertyInformation parameter on the specified bean object using the specified
	 * testValue.
	 * </p>
	 * 
	 * <p>
	 * The test is performed by setting the property of the specified bean to the specified testValue via the property
	 * setter method, then getting the value of the property via the property getter method and asserting that the
	 * obtained value matches the testValue. This tests the getter and setter methods of the property.
	 * </p>
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
		ValidationHelper.ensureExists("bean", "test property", bean);
		ValidationHelper.ensureExists("property", "test property", property);
		ValidationHelper.ensureExists("testValue", "test property", testValue);
		ValidationHelper.ensureExists("equalityTest", "test property", equalityTest);
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
				String message =
				        "Property [" + propertyName + "] getter did not return test value. Expected [" + testValue
				                + "] but getter returned [" + readMethodOutput + "].";
				AssertionUtils.fail(message);
			} else {
			}
		} catch (Exception e) {
			String message =
			        "Failed to test property [" + propertyName + "] due to Exception [" + e.getClass().getName()
			                + "]: [" + e.getMessage() + "].";
			throw new BeanTestException(message, e);
		}
	}

	/**
	 * Are the specified compatible?
	 * 
	 * @param classA
	 *            A type to compare.
	 * @param superType
	 *            Another type to compare. If it is possible that one of the types might be a superclass of the other,
	 *            specify that type here.
	 * 
	 * @return <code>true</code> if the specified types are compatible; <code>false</code> otherwise.
	 */
	protected boolean typesAreCompatible(Class<?> classA, Type superType) {
		Class<?> superClass = org.meanbean.util.Types.getRawType(superType);
		if ((!classA.isPrimitive()) && (!superClass.isPrimitive())) {
			return superClass.isAssignableFrom(classA);
		}
		return (classA.getSimpleName().equals(classA.getSimpleName()));
	}
}
