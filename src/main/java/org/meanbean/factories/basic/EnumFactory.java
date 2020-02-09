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
import org.meanbean.util.ValidationHelper;

/**
 * Concrete Factory that creates random Enum constants of the specified Enum type.
 * 
 * @author Graham Williamson
 */
public class EnumFactory extends RandomFactoryBase<Enum<?>> {

	/** Enum constants of the specified Enum type. */
	private final Enum<?>[] enumConstants;

	/**
	 * Construct a new Factory.
	 * 
	 * @param enumClass
	 *            The type of Enum to create Enum constants of.
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the required parameters are deemed illegal. For example, if either is null.
	 */
	public EnumFactory(Class<?> enumClass, RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
		ValidationHelper.ensureExists("enumClass", "construct EnumFactory", enumClass);
		if (!enumClass.isEnum()) {
			throw new IllegalArgumentException("Cannot create EnumFactory for non-Enum class.");
		}
		this.enumConstants = (Enum<?>[]) enumClass.getEnumConstants();
	}

	/**
	 * Create an Enum constant of the specified Enum type.
	 * 
	 * @return An Enum constant of the specified Enum type.
	 */
	@Override
	public Enum<?> create() {
		// Basis to randomly select enum constant from
		double random = getRandomValueGenerator().nextDouble();
		// Get ordinal from random number
		int ordinal = ((int) ((enumConstants.length - 1) * random));
		// Get enum constant from ordinal
		return enumConstants[ordinal];
	}
}
