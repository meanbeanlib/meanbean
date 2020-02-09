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

package org.meanbean.factories.equivalent;

import org.meanbean.lang.EquivalentFactory;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete Factory that always creates the same Enum constant of the specified Enum type.
 * 
 * @author Graham Williamson
 */
public class EquivalentEnumFactory implements EquivalentFactory<Enum<?>> {

	/** Enum constants of the specified Enum type. */
	private final Enum<?>[] enumConstants;

	/**
	 * Construct a new Factory.
	 * 
	 * @param enumClass
	 *            The type of Enum to create Enum constants of.
	 * 
	 * @throws IllegalArgumentException
	 *             If enumClass is deemed illegal. For example, if it is null.
	 */
	public EquivalentEnumFactory(Class<?> enumClass) throws IllegalArgumentException {
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
		return enumConstants[0];
	}
}
