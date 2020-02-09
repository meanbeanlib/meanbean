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

import org.meanbean.lang.Factory;

/**
 * Factory wrapper (decorator) that keeps count of the number of times <code>create()</code> is invoked, delegating the
 * actual creation of the object to the wrapped factory, specified at construction.
 * 
 * This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class InvocationCountingFactoryWrapper<T> implements Factory<T> {

	private final Factory<T> factory;

	private int invocationCount = 0;

	public InvocationCountingFactoryWrapper(Factory<T> factory) {
		this.factory = factory;
	}

	@Override
    public T create() {
		invocationCount++;
		return factory.create();
	}

	public int getInvocationCount() {
		return invocationCount;
	}
}
