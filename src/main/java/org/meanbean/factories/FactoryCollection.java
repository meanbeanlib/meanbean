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

/**
 * Defines a collection factories of different types of objects.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollection extends FactoryLookup {

	public static ServiceDefinition<FactoryCollection> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryCollection.class);
	}

	public static FactoryCollection getInstance() {
		return getServiceDefinition().getServiceFactory()
				.getFirst();
	}
	
	/**
	 * <p>
	 * Add the specified Factory to the collection.
	 * </p>
	 * 
	 * <p>
	 * If a Factory is already registered against the specified class, the existing registered Factory will be replaced
	 * with the Factory you specify here.
	 * </p>
	 * 
	 * @param clazz
	 *            The type of objects the Factory creates. The class type will be used to generate a key with which the
	 *            Factory can be retrieved from the collection at a later stage.
	 * @param factory
	 *            The Factory to add to the collection.
	 * 
	 * @throws IllegalArgumentException
	 *             If either of the required parameters are deemed illegal.
	 */
	void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException;
	
	/**
	 * <p>
	 * Add the specified FactoryLookup
	 * </p>
	 * 
	 * The factoryLookup will be first consulted when looking for a Factory  
	 */
	void addFactoryLookup(FactoryLookup factoryLookup);

}
