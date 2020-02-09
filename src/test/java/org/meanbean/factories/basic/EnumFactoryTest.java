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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class EnumFactoryTest extends BasicFactoryTestBase<Enum<?>> {

	@Override
	protected Factory<Enum<?>> createFactory(RandomValueGenerator randomValueGenerator) {
		return new EnumFactory(Color.class, randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { 0.3, 0.6, 1 }, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullEnumClass() throws Exception {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, null, null, null, null, null);
		new EnumFactory(null, randomValueGenerator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventIllegalEnumEnumClass() throws Exception {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, null, null, null, null, null);
		new EnumFactory(String.class, randomValueGenerator);
	}

	@Test
	public void createShouldReturnEachEnum() throws Exception {
		double RED_DOUBLE = 1.0 / Color.values().length;
		double GREEN_DOUBLE = 2.0 / Color.values().length;
		double BLUE_DOUBLE = 3.0 / Color.values().length;
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { RED_DOUBLE, GREEN_DOUBLE,
		                BLUE_DOUBLE }, null);
		EnumFactory enumFactory = new EnumFactory(Color.class, randomValueGenerator);
		assertThat("Incorrect enum.", (Color) enumFactory.create(), is(Color.RED));
		assertThat("Incorrect enum.", (Color) enumFactory.create(), is(Color.GREEN));
		assertThat("Incorrect enum.", (Color) enumFactory.create(), is(Color.BLUE));
	}

	static enum Color {
		RED, GREEN, BLUE
	}
}
