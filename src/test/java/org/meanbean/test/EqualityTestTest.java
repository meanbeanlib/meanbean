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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;

public class EqualityTestTest {

	private static final BeanFactory beanFactory = new BeanFactory();

	@Test
	public void absoluteShouldReturnTrueForSameInstance() throws Exception {
		Bean x = beanFactory.create();
		assertThat("Incorrect EqualityTest result.", EqualityTest.ABSOLUTE.test(x, x), is(true));
	}

	@Test
	public void absoluteShouldReturnFalseForDifferentInstances() throws Exception {
		Bean x = beanFactory.create();
		Bean y = beanFactory.create();
		assertThat("Incorrect EqualityTest result.", EqualityTest.ABSOLUTE.test(x, y), is(false));
	}

	@Test
	public void logicalShouldReturnTrueForLogicallyEquivalentObjects() throws Exception {
		Bean x = beanFactory.create();
		Bean y = beanFactory.create();
		assertThat("Incorrect EqualityTest result.", EqualityTest.LOGICAL.test(x, y), is(true));
	}

	@Test
	public void logicalShouldReturnFalseForNonLogicallyEquivalentObjects() throws Exception {
		Bean x = beanFactory.create();
		Bean y = beanFactory.create();
		y.setName(y.getName() + "_DIFFERENT");
		assertThat("Incorrect EqualityTest result.", EqualityTest.LOGICAL.test(x, y), is(false));
	}
}
