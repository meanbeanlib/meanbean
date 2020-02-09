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

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

/**
 * Abstract base class for a Factory that creates random objects of the specified type.
 * 
 * @author Graham Williamson
 * @param <T>
 *            The data type of the object this Factory creates.
 */
public abstract class RandomFactoryBase<T> implements Factory<T> {

	/** Random number generator used by the factory to generate random values. */
	private final RandomValueGenerator randomValueGenerator;

	/**
	 * Construct a new Factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public RandomFactoryBase(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		ValidationHelper.ensureExists("randomValueGenerator", "construct Factory", randomValueGenerator);
		this.randomValueGenerator = randomValueGenerator;
	}

	/**
	 * Get the random value generator.
	 * 
	 * @return A random value generator.
	 */
    public final RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	/**
	 * Create a new object of the specified type.
	 * 
	 * @return A new object of the specified type.
	 */
	@Override
    public abstract T create();
}
