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
import org.meanbean.util.ServiceDefinition;

import java.lang.reflect.Type;
import java.util.function.Supplier;

/**
 * For looking up Factory instances
 */
public interface FactoryLookup {

	/**
	 * <p>
	 * Get the Factory registered for the specified class.
	 * </p>
	 * 
	 * <p>
	 * To check whether a Factory is registered for a specified class, please refer to
	 * <code>hasFactory(Class&lt;?&gt; clazz);</code>.
	 * </p>
	 * 
	 * @param type
	 *            The type the Factory is registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return The requested Factory.
	 * 
	 * @throws IllegalArgumentException
	 *             If the class is deemed illegal.
	 * @throws NoSuchFactoryException
	 *             If this does not contain a Factory registered against the specified class.
	 */
	 <T> Factory<T> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException;

	/**
	 * Does this contain a Factory registered against the specified class?
	 * 
	 * @param type
	 *            The type a Factory could be registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return <code>true</code> if the collection contains a Factory registered for the specified class;
	 *         <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the clazz is deemed illegal.
	 */
	boolean hasFactory(Type type) throws IllegalArgumentException;
	
	default <T> Factory<T> getFactoryIfAvailable(Type type, Supplier<Factory<T>> fallback) {
		if (hasFactory(type)) {
			return getFactory(type);
		}
		return fallback.get();
	}

	public static ServiceDefinition<FactoryLookup> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryLookup.class);
	}

	public static FactoryLookup getInstance() {
		return getServiceDefinition().getServiceFactory()
				.getFirst();
	}
}
