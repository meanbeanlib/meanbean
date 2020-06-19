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

package org.meanbean.test.beans;

import org.junit.Test;
import org.meanbean.test.BeanVerifier;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class FluentPropertyBeanTest {

	@Test
	public void testOk() {
		BeanVerifier.forClass(FluentPropertyBean.class)
				.editSettings()
				.setDefaultIterations(1)
				.edited()
				.verify();
	}

	@Test
	public void testFails() {
		assertThatCode(() -> {
			runTestForcingException();
		}).hasRootCauseMessage("fake exception");
	}

	private void runTestForcingException() {
		BeanVerifier.forClass(FluentPropertyBean.class)
				.editSettings()
				.registerFactory(FluentPropertyBean.class, () -> {
					FluentPropertyBean bean = mock(FluentPropertyBean.class);
					doThrow(new IllegalStateException("fake exception"))
							.when(bean)
							.setData(anyString());
					return bean;
				})
				.setDefaultIterations(1)
				.edited()
				.verify();
	}
}
