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

package org.meanbean.factories;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.util.ServiceFactory;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ArrayFactoryLookupTest {

	private ArrayFactoryLookup arrayFactoryCollection;

	@Before
	public void setUp() {
		ServiceFactory.createContext(this);
		arrayFactoryCollection = new ArrayFactoryLookup();
	}

	@Test
	public void hasFactory() throws Exception {
		assertThat(arrayFactoryCollection.hasFactory(UUID[][].class))
				.isEqualTo(true);
	}

	@Test
	public void getFactory() throws Exception {
		Factory<UUID[][]> factory = arrayFactoryCollection.getFactory(UUID[][].class);
		UUID[][] matrix = factory.create();

		assertThat(matrix)
				.hasSizeLessThanOrEqualTo(arrayFactoryCollection.getMaxSize());
		for (UUID[] array : matrix) {
			assertThat(array)
					.hasSizeLessThanOrEqualTo(arrayFactoryCollection.getMaxSize());
		}
	}

	@Test
	public void maxArrayLength() {
		assertThat(arrayFactoryCollection.getMaxSize())
				.isLessThanOrEqualTo(8);
	}

	@Test
	public void noBeanTesterExceptionForArrayBean() {
		assertThatCode(() -> {
			new BeanTester().testBean(ArrayBean.class);
		}).doesNotThrowAnyException();
	}

	public static class ArrayBean {
		private String[] values;

		public String[] getValues() {
			return values;
		}

		public void setValues(String[] values) {
			this.values = values;
		}

	}
}
