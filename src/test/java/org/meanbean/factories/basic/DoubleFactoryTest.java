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

public class DoubleFactoryTest extends BasicFactoryTestBase<Double> {

	@Override
	protected Factory<Double> createFactory(RandomValueGenerator randomValueGenerator) {
		return new DoubleFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		boolean basedOnMaximum = true;
		boolean positive = true;
		return new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { 0.61, 0.62 }, new boolean[] {
		        basedOnMaximum, positive, basedOnMaximum, positive });
	}

	private RandomValueGenerator createRandomNumberGenerator(boolean positive, boolean basedOnMaximum,
	        double randomNumber) {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { randomNumber },
		                new boolean[] { basedOnMaximum, positive });
		return randomValueGenerator;
	}

	@Test
	public void createShouldReturnPositiveDoubleBasedOnMaximumValue() throws Exception {
		double randomNumber = 0.6;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(true, true, randomNumber));
		Double number = factory.create();
		Double expectedNumber = Double.MAX_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeDoubleBasedOnMaximumValue() throws Exception {
		double randomNumber = 0.2;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(false, true, randomNumber));
		Double number = factory.create();
		Double expectedNumber = -(Double.MAX_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnPositiveDoubleBasedOnMinimumValue() throws Exception {
		double randomNumber = 0.33;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(true, false, randomNumber));
		Double number = factory.create();
		Double expectedNumber = Double.MIN_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeDoubleBasedOnMinimumValue() throws Exception {
		double randomNumber = 0.756;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(false, false, randomNumber));
		Double number = factory.create();
		Double expectedNumber = -(Double.MIN_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}
}
