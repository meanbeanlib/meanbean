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

import org.junit.Test;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;

public class SignificantObjectPropertyEqualityConsistentAsserterTest {

	private final ObjectPropertyEqualityConsistentAsserter objectPropertyEqualityConsistentAsserter =
	        new SignificantObjectPropertyEqualityConsistentAsserter();

	private final BeanFactory beanFactory = new BeanFactory();

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullPropertyName() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent(null, new Object(), new Object(), new Object(),
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullOriginalObject() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", null, new Object(), new Object(),
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullModifiedObject() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", new Object(), null, new Object(),
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullOriginalPropertyValue() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", new Object(), new Object(), null,
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullNewPropertyValue() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", new Object(), new Object(),
		        new Object(), null);
	}

	@Test(expected = AssertionError.class)
	public void assertConsistentShouldThrowAssertionErrorWhenValuesDifferButObjectsStillEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		Long originalPropertyValue = 2L;
		Long newPropertyValue = 3L;
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}

	@Test
	public void assertConsistentShouldNotThrowAssertionErrorWhenValuesDifferAndObjectsNotEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		String originalPropertyValue = originalObject.getName();
		String newPropertyValue = modifiedObject.getName() + "_DIFFERENT";
		modifiedObject.setName(newPropertyValue);
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}

	@Test(expected = AssertionError.class)
	public void assertConsistentShouldThrowAssertionErrorWhenValuesSameButObjectsNotEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		modifiedObject.setName(modifiedObject.getName() + "_DIFFERENT");
		Long originalPropertyValue = 2L;
		Long newPropertyValue = 2L;
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}

	@Test
	public void assertConsistentShouldNotThrowAssertionErrorWhenValuesSameAndObjectsEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		Long originalPropertyValue = 2L;
		Long newPropertyValue = 2L;
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}
}
