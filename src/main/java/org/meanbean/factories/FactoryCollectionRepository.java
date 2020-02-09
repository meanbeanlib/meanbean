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
import org.meanbean.util.ServiceDefinition;
import org.meanbean.util.ServiceFactory;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * Mutable FactoryCollection that delegates to other FactoryCollection implementations
 */
@MetaInfServices
@Order(1000)
public class FactoryCollectionRepository implements FactoryCollection {

	private final List<FactoryLookup> factoryLookups = new CopyOnWriteArrayList<>();
	
	@Override
	public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		factoryCollections().forEach(factoryCollection -> factoryCollection.addFactory(clazz, factory));
	}

	@Override
	public void addFactoryLookup(FactoryLookup factoryLookup) {
		factoryLookups.add(0, factoryLookup);
	}

	@Override
	public <T> Factory<T> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
		Optional<Factory<T>> resultOptional = factoryLookups()
				.filter(factoryCollection -> factoryCollection.hasFactory(type))
				.findFirst()
				.map(factoryCollection -> factoryCollection.getFactory(type));

		if (resultOptional.isPresent()) {
			return resultOptional.get();
		}

		throw new NoSuchFactoryException("No factory found for " + type);
	}

	@Override
	public boolean hasFactory(Type type) throws IllegalArgumentException {
		return factoryLookups().anyMatch(factoryCollection -> factoryCollection.hasFactory(type));
	}

	protected Stream<FactoryLookup> factoryLookups() {
		Stream<FactoryLookup> lookups = servicesFrom(FactoryLookup.getServiceDefinition());
		Stream<FactoryLookup> services = Stream.concat(lookups, factoryCollections())
				.sorted(ServiceFactory.getComparator());
		return Stream.concat(factoryLookups.stream(), services);
	}

	protected Stream<FactoryCollection> factoryCollections() {
		return servicesFrom(FactoryCollection.getServiceDefinition());
	}

	private <T> Stream<T> servicesFrom(ServiceDefinition<T> definition) {
		return definition.getServiceFactory()
				.getAll()
				.stream()
				.filter(factoryCollection -> factoryCollection != this);
	}

}
