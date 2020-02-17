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

import org.meanbean.lang.Factory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple concrete implementation of the FactoryCollection interface that should be used for testing only.
 * 
 * @author Graham Williamson
 */
public class SimpleFactoryCollection implements FactoryCollection {

	private final Map<Type, Factory<?>> factories = new HashMap<>();

	@Override
    public boolean hasFactory(Type type) throws IllegalArgumentException {
		return factories.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	@Override
    public <T> Factory<T> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
		return (Factory<T>) factories.get(type);
	}

	@Override
    public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		factories.put(clazz, factory);
	}

	@Override
	public void addFactoryLookup(FactoryLookup factoryLookup) {
		throw new UnsupportedOperationException();
	}
}
