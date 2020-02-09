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

public class CharacterFactoryTest extends BasicFactoryTestBase<Character> {

	private static final double RANDOM_DOUBLE_1 = 0.45;

	private static final double RANDOM_DOUBLE_2 = 0.9345;

	@Override
	protected Factory<Character> createFactory(RandomValueGenerator randomValueGenerator) {
		return new CharacterFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { RANDOM_DOUBLE_1,
		        RANDOM_DOUBLE_2 }, null);
	}

	@Test
	public void createShouldReturnCharactersMatchingRandomIntegers() throws Exception {
		Factory<Character> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random character.", factory.create().charValue(),
		        is((char) (Character.MAX_VALUE * RANDOM_DOUBLE_1)));
		assertThat("Incorrect random character.", factory.create().charValue(),
		        is((char) (Character.MAX_VALUE * RANDOM_DOUBLE_2)));
	}
}
