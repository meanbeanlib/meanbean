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

import org.meanbean.lang.EquivalentFactory;

/**
 * A factory that creates CounterDrivenEqualsBeans. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class CounterDrivenEqualsBeanFactory implements EquivalentFactory<CounterDrivenEqualsBean> {

	public static final String NAME = "TEST_NAME";

	private final int falseInvocationIndex;

	public CounterDrivenEqualsBeanFactory(int falseInvocationIndex) {
		this.falseInvocationIndex = falseInvocationIndex;
	}

	@Override
    public CounterDrivenEqualsBean create() {
		CounterDrivenEqualsBean bean = new CounterDrivenEqualsBean(falseInvocationIndex);
		bean.setName(NAME);
		return bean;
	}
}
