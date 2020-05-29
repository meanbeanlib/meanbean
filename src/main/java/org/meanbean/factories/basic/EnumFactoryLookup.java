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

import org.kohsuke.MetaInfServices;
import org.meanbean.factories.FactoryLookup;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;
import org.meanbean.util.RandomValueGenerator;

import java.lang.reflect.Type;

/**
 * FactoryLookup for EnumFactory instances
 */
@Order(4100)
@MetaInfServices
public class EnumFactoryLookup implements FactoryLookup {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Factory<T> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
		return (Factory<T>) new EnumFactory((Class<?>) type, RandomValueGenerator.getInstance());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean hasFactory(Type type) throws IllegalArgumentException {
		return type instanceof Class && ((Class) type).isEnum();
	}

}
