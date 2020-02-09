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

public class IntegerFactoryTest extends BasicFactoryTestBase<Integer> {

	private static final int RANDOM_INT_1 = -2147483567;

	private static final int RANDOM_INT_2 = 2143793567;

	@Override
	protected Factory<Integer> createFactory(RandomValueGenerator randomValueGenerator) {
		return new IntegerFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, new int[] { RANDOM_INT_1, RANDOM_INT_2 }, null, null, null,
		        null);
	}

	@Test
	public void createShouldReturnExpectedIntegers() throws Exception {
		Factory<Integer> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Integer.", factory.create(), is(RANDOM_INT_1));
		assertThat("Incorrect random Integer.", factory.create(), is(RANDOM_INT_2));
	}
}
