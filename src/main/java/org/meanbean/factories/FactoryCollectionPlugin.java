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

import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceDefinition;

import java.util.List;

/**
 * Defines a plugin that will register Factories with the specified FactoryCollection.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollectionPlugin {

	public static ServiceDefinition<FactoryCollectionPlugin> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryCollectionPlugin.class);
	}

	public static List<FactoryCollectionPlugin> getInstances() {
		return getServiceDefinition().getServiceFactory()
				.getAll();
	}
	
	/**
	 * Initialize the plugin, adding Factories to the FactoryCollection.
	 * 
	 * @param factoryCollection
	 *            A FactoryCollection that Factory objects can be added to.
	 * @param randomValueGenerator RandomValueGenerator that can be used by Factory objects.
	 */
	void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator);
}
