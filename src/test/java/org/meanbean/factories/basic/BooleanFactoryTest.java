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

public class BooleanFactoryTest extends BasicFactoryTestBase<Boolean> {

	private static final Boolean RANDOM_BOOLEAN_1 = false;

	private static final Boolean RANDOM_BOOLEAN_2 = true;

	@Override
	protected Factory<Boolean> createFactory(RandomValueGenerator randomValueGenerator) {
		return new BooleanFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, null, null, null, new boolean[] { RANDOM_BOOLEAN_1,
		        RANDOM_BOOLEAN_2 });
	}

	@Test
	public void createShouldReturnExpectedBooleans() throws Exception {
		Factory<Boolean> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Boolean.", factory.create(), is(RANDOM_BOOLEAN_1));
		assertThat("Incorrect random Boolean.", factory.create(), is(RANDOM_BOOLEAN_2));
	}
}
