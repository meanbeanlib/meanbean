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

import org.meanbean.bean.info.BeanInformationException;
import org.meanbean.lang.EquivalentFactory;

/**
 * <p>
 * Defines a means of verifying that the equals logic implemented by a type is affected in the expected manner when
 * changes are made to the property values of instances of the type. <br>
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
 * @author Graham Williamson
 */
interface EqualsMethodPropertySignificanceVerifier {

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the property values of instances of the type. <br>
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
	 * individually and the equality is reassessed. <br>
	 * </p>
	 * 
	 * <p>
	 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br>
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
	void verifyEqualsMethod(EquivalentFactory<?> factory, String... insignificantProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError;

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is affected in the expected
	 * manner when changes are made to the property values of instances of the type. <br>
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
	 * individually and the equality is reassessed. <br>
	 * </p>
	 * 
	 * <p>
	 * For the test to function correctly, you must specify all properties that are not used in the equals logic. <br>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test the
	 *            equals logic. The factory must create logically equivalent but different actual instances of the type
	 *            upon each invocation of <code>create()</code> in order for the test to be meaningful.
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
	void verifyEqualsMethod(EquivalentFactory<?> factory, Configuration customConfiguration,
	        String... insignificantProperties) throws IllegalArgumentException, BeanInformationException,
	        BeanTestException, AssertionError;
}
