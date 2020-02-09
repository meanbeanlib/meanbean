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

/**
 * A bean whose equals method is driven by a counter that returns false on a specified invocation number. This class
 * should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class CounterDrivenEqualsBean extends Bean {

	private final int falseInvocationIndex;

	private int counter;

	public CounterDrivenEqualsBean(int falseInvocationIndex) {
		this.falseInvocationIndex = falseInvocationIndex;
	}

	@Override
	public boolean equals(Object obj) {
		if (counter++ == falseInvocationIndex) {
			return false;
		}
		return super.equals(obj);
	}
}
