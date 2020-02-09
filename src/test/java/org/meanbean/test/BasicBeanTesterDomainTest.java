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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.test.beans.domain.EmployeeWithBrokenManagerSetter;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasicBeanTesterDomainTest {

	private BeanTester beanTester;

	@Before
	public void before() {
		beanTester = new BeanTester();
	}

	@Test
	public void testBeanShouldThrowAssertionErrorWhenEmployeeManagerSetterDoesNotChangeFieldAsExpected()
	        throws Exception {
		AssertionError assertionError = null;
		try {
			beanTester.testBean(EmployeeWithBrokenManagerSetter.class);
			fail("AssertionError should have been thrown.");
		} catch (AssertionError e) {
			assertionError = e;
		}
		assertTrue(assertionError.getMessage().startsWith("Property [manager] getter did not return test value."));
	}
}
