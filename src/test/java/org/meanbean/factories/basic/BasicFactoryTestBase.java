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

package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public abstract class BasicFactoryTestBase<T> {

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullRandomNumberGenerator() throws Exception {
		createFactory(null);
	}

	@Test
	public void createShouldReturnNewObjectEachInvocation() throws Exception {
		Factory<T> factory = createFactory(createRandomNumberGenerator());
		T createdObject1 = factory.create();
		T createdObject2 = factory.create();
		assertThat("Factory does not create new objects.", createdObject1, is(not(sameInstance(createdObject2))));
	}

	@Test
	public void createShouldReturnDifferentValuesEachInvocation() throws Exception {
		Factory<T> factory = createFactory(createRandomNumberGenerator());
		T createdObject1 = factory.create();
		T createdObject2 = factory.create();
		assertThat("Factory does not create different values.", createdObject1, is(not(createdObject2)));
	}

	protected abstract RandomValueGenerator createRandomNumberGenerator();

	protected abstract Factory<T> createFactory(RandomValueGenerator randomValueGenerator);
}
