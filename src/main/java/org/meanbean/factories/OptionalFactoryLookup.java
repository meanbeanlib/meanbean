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
import org.meanbean.util.Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static java.util.Collections.unmodifiableMap;
import static org.meanbean.util.Types.getRawType;

/**
 * FactoryCollection for Optional, OptionalInt, OptionalLong, and OptionalDouble types
 */
@Order(4000)
@MetaInfServices
public class OptionalFactoryLookup implements FactoryLookup {

	private static final Map<Class<?>, Class<?>> OPTIONAL_TO_ITEM_TYPE_MAP = createOptionalTypeMap();

	@SuppressWarnings("unchecked")
	@Override
	public <T> Factory<T> getFactory(Type typeToken) throws IllegalArgumentException, NoSuchFactoryException {
		return (Factory<T>) createOptionalPopulatingFactory(typeToken);
	}

	private static Map<Class<?>, Class<?>> createOptionalTypeMap() {
		Map<Class<?>, Class<?>> map = new HashMap<>();
		map.put(Optional.class, null);
		map.put(OptionalInt.class, Integer.class);
		map.put(OptionalLong.class, Long.class);
		map.put(OptionalDouble.class, Double.class);
		return unmodifiableMap(map);
	}

	private Factory<?> findItemFactory(Type itemType) {
		FactoryCollection factoryCollection = FactoryCollection.getInstance();
		try {
			return factoryCollection.getFactory(itemType);
		} catch (NoSuchFactoryException e) {
			return factoryCollection.getFactory(void.class);
		}
	}

	@Override
	public boolean hasFactory(Type type) {
		Class<?> clazz = Types.getRawType(type);
		return !clazz.equals(void.class) && isAssignableToOptional(clazz);
	}

	private boolean isAssignableToOptional(Class<?> clazz) {
		for (Class<?> optionalType : OPTIONAL_TO_ITEM_TYPE_MAP.keySet()) {
			if (optionalType.isAssignableFrom(clazz)) {
				return true;
			}
		}
		return false;
	}

	private Type findElementType(Type type, int index) {
		if (type instanceof ParameterizedType) {
			return ((ParameterizedType) type).getActualTypeArguments()[index];
		}
		return String.class;
	}

	private Factory<?> createOptionalPopulatingFactory(Type typeToken) {
		Class<?> rawType = getRawType(typeToken);
		return findInstanceFactory(typeToken, rawType);
	}

	@SuppressWarnings("unchecked")
	private <T> Factory<T> findInstanceFactory(Type type, Class<?> rawType) {
		Class<?> itemType = OPTIONAL_TO_ITEM_TYPE_MAP.get(rawType);
		Factory<?> itemFactory = itemType == null
				? findItemFactory(findElementType(type, 0))
				: findItemFactory(itemType);

		if (rawType.equals(Optional.class)) {
			return () -> (T) Optional.ofNullable(itemFactory.create());
		}

		if (rawType.equals(OptionalInt.class)) {
			return () -> (T) OptionalInt.of((Integer) itemFactory.create());
		}

		if (rawType.equals(OptionalLong.class)) {
			return () -> (T) OptionalLong.of((Long) itemFactory.create());
		}

		if (rawType.equals(OptionalDouble.class)) {
			return () -> (T) OptionalDouble.of((Double) itemFactory.create());
		}

		throw new IllegalArgumentException("Unknown optional type:" + type);
	}
}
