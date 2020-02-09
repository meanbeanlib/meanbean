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

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class FloatFactoryTest extends BasicFactoryTestBase<Float> {

	@Override
	protected Factory<Float> createFactory(RandomValueGenerator randomValueGenerator) {
		return new FloatFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		boolean basedOnMaximum = true;
		boolean positive = true;
		return new ArrayBasedRandomValueGenerator(null, null, null, new float[] { 0.61f, 0.62f }, null, new boolean[] {
		        basedOnMaximum, positive, basedOnMaximum, positive });
	}

	private RandomValueGenerator createRandomNumberGenerator(boolean positive, boolean basedOnMaximum,
	        float randomNumber) {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, null, null, new float[] { randomNumber }, null, new boolean[] {
		                basedOnMaximum, positive });
		return randomValueGenerator;
	}

	@Test
	public void createShouldReturnPositiveFloatBasedOnMaximumValue() throws Exception {
		float randomNumber = 0.6f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(true, true, randomNumber));
		Float number = factory.create();
		Float expectedNumber = Float.MAX_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeFloatBasedOnMaximumValue() throws Exception {
		float randomNumber = 0.2f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(false, true, randomNumber));
		Float number = factory.create();
		Float expectedNumber = -(Float.MAX_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnPositiveFloatBasedOnMinimumValue() throws Exception {
		float randomNumber = 0.33f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(true, false, randomNumber));
		Float number = factory.create();
		Float expectedNumber = Float.MIN_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeFloatBasedOnMinimumValue() throws Exception {
		float randomNumber = 0.756f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(false, false, randomNumber));
		Float number = factory.create();
		Float expectedNumber = -(Float.MIN_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}
}
