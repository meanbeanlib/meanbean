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

public class ByteFactoryTest extends BasicFactoryTestBase<Byte> {

	private static final byte RANDOM_BYTE_1 = -17;

	private static final byte RANDOM_BYTE_2 = 97;

	@Override
	protected Factory<Byte> createFactory(RandomValueGenerator randomValueGenerator) {
		return new ByteFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(new byte[][] { { RANDOM_BYTE_1 }, { RANDOM_BYTE_2 } }, null, null,
		        null, null, null);
	}

	@Test
	public void createShouldReturnExpectedBytes() throws Exception {
		Factory<Byte> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Byte.", factory.create(), is(RANDOM_BYTE_1));
		assertThat("Incorrect random Byte.", factory.create(), is(RANDOM_BYTE_2));
	}
}
