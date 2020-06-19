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

import org.meanbean.util.AssertionUtils;
import org.meanbean.util.ValidationHelper;

import java.util.Arrays;
import java.util.function.Supplier;

import static org.meanbean.test.internal.$EqualsBuilder.objectsEqual;

/**
 * <p>
 * Concrete ObjectPropertyEqualityConsistentAsserter that provides a means of verifying whether the equality of an
 * object and its property is consistent. <br>
 * </p>
 * 
 * <p>
 * This object is specific to testing properties that are "significant" in the implemented equals logic of a given
 * object. Significant properties are those considered by the equals logic. Whether a property is significant or not
 * changes the test's expectation of whether the property will affect the equality of an object. <br>
 * </p>
 * 
 * <p>
 * Significant properties <strong>should</strong> affect the equality of an object if any of the properties have
 * changed.
 * </p>
 * 
 * @author Graham Williamson
 */
class SignificantObjectPropertyEqualityConsistentAsserter implements ObjectPropertyEqualityConsistentAsserter {

	/**
	 * <p>
	 * Assert that the equality of two logically equivalent objects is consistent when a change is made to a property of
	 * one of the objects. <br>
	 * </p>
	 * 
	 * <p>
	 * It is permissible to check the equality of two objects when no change has been made to the named property. This
	 * check is performed when logically equivalent objects are passed to the <i>originalPropertyValue</i> and
	 * <i>newPropertyValue</i> arguments.
	 * </p>
	 * 
	 * @param propertyName
	 *            The name of the property that may have changed.
	 * @param originalObject
	 *            The object that the modified object was logically equivalent to prior to its modification.
	 * @param modifiedObject
	 *            The modified object, which was logically equivalent to the original object prior to the modification
	 *            of its named property.
	 * @param originalPropertyValue
	 *            The original value of the modified property. This should still be the value of the named property in
	 *            the original object.
	 * @param newPropertyValue
	 *            The new value of the named property in the modified object.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the parameters are deemed illegal. For example, if any are <code>null</code>.
	 * @throws AssertionError
	 *             If the equality of the modified object is inconsistent with the changes made to its property.
	 */
	@Override
    public void assertConsistent(String propertyName, Object originalObject, Object modifiedObject,
	        Object originalPropertyValue, Object newPropertyValue) throws IllegalArgumentException, AssertionError {
		ValidationHelper.ensureExists("propertyName", "assert consistency of equals", propertyName);
		ValidationHelper.ensureExists("originalObject", "assert consistency of equals", originalObject);
		ValidationHelper.ensureExists("modifiedObject", "assert consistency of equals", modifiedObject);
		ValidationHelper.ensureExists("originalPropertyValue", "assert consistency of equals", originalPropertyValue);
		ValidationHelper.ensureExists("newPropertyValue", "assert consistency of equals", newPropertyValue);

		boolean newPropertyValueEqualsOriginalPropertyValue = objectsEqual(newPropertyValue, originalPropertyValue);
		boolean originalObjectEqualsModifiedObject = objectsEqual(originalObject, modifiedObject);
		Supplier<String> variableString = formatVariableString(propertyName, originalPropertyValue, newPropertyValue);

		if (originalObjectEqualsModifiedObject && !newPropertyValueEqualsOriginalPropertyValue) {
			String message = "objects that differ due to supposedly significant property [" + propertyName
					+ "] were considered equal. " + variableString.get() + ". is property [" + propertyName
					+ "] actually insignificant?";
			AssertionUtils.fail(message);

		} else if (!originalObjectEqualsModifiedObject && newPropertyValueEqualsOriginalPropertyValue) {
			String message = "objects that should be equal were considered unequal when testing significant " + "property ["
					+ propertyName + "]. " + variableString.get() + ". is equals incorrect?";
			AssertionUtils.fail(message);
		}
	}

	static Supplier<String> formatVariableString(String propertyName, Object originalPropertyValue, Object newPropertyValue) {
		return () -> {
			String originalPropertyString = buildToString(originalPropertyValue);
			String newPropertyString = buildToString(newPropertyValue);
			return "(\nx." + propertyName + "=[" + originalPropertyString
					+ "]\nvs\ny." + propertyName + "=["
					+ newPropertyString + "]\n)";
		};
	}

	private static String buildToString(Object obj) {
		if (obj != null && obj.getClass().isArray()) {
			Object[] objectArray = { obj };
			String str = Arrays.deepToString(objectArray);
			return str.substring(1, str.length() - 1);
		}
		return String.valueOf(obj);
	}
}
