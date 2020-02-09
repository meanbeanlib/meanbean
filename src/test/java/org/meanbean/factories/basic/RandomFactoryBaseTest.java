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

import org.junit.Test;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class RandomFactoryBaseTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullRandomNumberGenerator() throws Exception {
		new SimpleFactory<String>(null);
	}

	@Test
	public void getRandomNumberGeneratorShouldReturnRandomNumberGenerator() throws Exception {
		RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();
		SimpleFactory<String> factory = new SimpleFactory<String>(randomValueGenerator);
		assertThat("RandomNumberGenerator should not be null.", factory.getRandomValueGenerator(), is(not(nullValue())));
	}

	static class SimpleFactory<T> extends RandomFactoryBase<T> {

		public SimpleFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
			super(randomValueGenerator);
		}

		@Override
		public T create() {
			return null; // Not relevant in testing the base class - do nothing
		}
	}

}
