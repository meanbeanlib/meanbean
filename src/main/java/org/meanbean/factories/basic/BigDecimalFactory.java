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

import org.meanbean.util.RandomValueGenerator;

import java.math.BigDecimal;

/**
 * Concrete Factory that creates random BigDecimal objects.
 * 
 * @author Graham Williamson
 */
public final class BigDecimalFactory extends RandomFactoryBase<BigDecimal> {

	/**
	 * Construct a new BigDecimal object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public BigDecimalFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new Double object.
	 * 
	 * @return A new Double object.
	 */
	@Override
	public BigDecimal create() {
		return new BigDecimal(getRandomValueGenerator().nextLong());
	}
}
