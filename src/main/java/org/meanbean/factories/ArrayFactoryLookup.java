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

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.Types;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import static org.meanbean.util.Types.getRawType;

/**
 * FactoryCollection for array types
 */
@Order(5000)
@MetaInfServices
public class ArrayFactoryLookup implements FactoryLookup {

	private final RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();
	private int maxSize = 8;

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxArrayLength) {
		this.maxSize = maxArrayLength;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Factory<T> getFactory(Type typeToken) throws IllegalArgumentException, NoSuchFactoryException {
		return () -> (T) randomArray(typeToken);
	}

	@Override
	public boolean hasFactory(Type type) {
		return getRawType(type).isArray();
	}

	private Object randomArray(Type typeToken) {
		Class<?> clazz = Types.getRawType(typeToken);
		int length = randomValueGenerator.nextInt(maxSize);
		Factory<?> componentFactory = getComponentFactory(clazz);
		Object array = Array.newInstance(clazz.getComponentType(), length);
		for (int i = 0; i < length; i++) {
			Array.set(array, i, componentFactory.create());
		}
		return  array;
	}

	private Factory<?> getComponentFactory(Class<?> clazz) {
		FactoryCollection instance = FactoryCollection.getInstance();
		return instance.getFactory(clazz.getComponentType());
	}
}
