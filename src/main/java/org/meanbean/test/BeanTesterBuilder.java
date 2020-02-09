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

package org.meanbean.test;

import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryLookup;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanTesterBuilder {

	private RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();

	private FactoryCollection factoryCollection = FactoryCollection.getInstance();

	private FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();

	private BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	private BeanPropertyTester beanPropertyTester = new BeanPropertyTester();

	private Map<Class<?>, Configuration> customConfigurations = new ConcurrentHashMap<>();

	private Configuration defaultConfiguration;

	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	public BeanTesterBuilder setRandomValueGenerator(RandomValueGenerator randomValueGenerator) {
		this.randomValueGenerator = randomValueGenerator;
		return this;
	}

	public FactoryCollection getFactoryCollection() {
		return factoryCollection;
	}

	public BeanTesterBuilder setFactoryCollection(FactoryCollection factoryCollection) {
		this.factoryCollection = factoryCollection;
		return this;
	}

	public FactoryLookupStrategy getFactoryLookupStrategy() {
		return factoryLookupStrategy;
	}

	public BeanTesterBuilder setFactoryLookupStrategy(FactoryLookupStrategy factoryLookupStrategy) {
		this.factoryLookupStrategy = factoryLookupStrategy;
		return this;
	}

	public BeanInformationFactory getBeanInformationFactory() {
		return beanInformationFactory;
	}

	public BeanTesterBuilder setBeanInformationFactory(BeanInformationFactory beanInformationFactory) {
		this.beanInformationFactory = beanInformationFactory;
		return this;
	}

	public BeanPropertyTester getBeanPropertyTester() {
		return beanPropertyTester;
	}

	public BeanTesterBuilder setBeanPropertyTester(BeanPropertyTester beanPropertyTester) {
		this.beanPropertyTester = beanPropertyTester;
		return this;
	}

	public Configuration getDefaultConfiguration() {
		return defaultConfiguration;
	}

	public BeanTesterBuilder setDefaultConfiguration(Configuration defaultConfiguration) {
		this.defaultConfiguration = defaultConfiguration;
		return this;
	}

	public <T> BeanTesterBuilder registerFactory(Class<T> clazz, Factory<? extends T> factory) {
		getFactoryCollection().addFactory(clazz, factory);
		return this;
	}

	public BeanTesterBuilder registerCustomConfiguration(Class<?> beanClass, Configuration configuration) {
		customConfigurations.put(beanClass, configuration);
		return this;
	}

	/**
	 * Register factory for an inheritance type hierarchy
	 */
	public <T> BeanTesterBuilder registerTypeHierarchyFactory(Class<T> baseType, Factory<T> factory) {
		getFactoryCollection().addFactoryLookup(new FactoryLookup() {

			@Override
			public boolean hasFactory(Type clazz) throws IllegalArgumentException {
				return clazz instanceof Class && baseType.isAssignableFrom((Class<?>) clazz);
			}

			@SuppressWarnings("unchecked")
			@Override
			public <E> Factory<E> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
				if (hasFactory(type)) {
					return (Factory<E>) factory;
				}
				throw new NoSuchFactoryException("No factory for " + type);
			}
		});
		return this;
	}

	public BeanTester build() {
		BeanTester beanTester = new BeanTester(
				randomValueGenerator,
				factoryCollection,
				factoryLookupStrategy,
				beanInformationFactory,
				beanPropertyTester,
				customConfigurations,
				defaultConfiguration);

		randomValueGenerator = null;
		factoryCollection = null;
		factoryLookupStrategy = null;
		beanInformationFactory = null;
		beanPropertyTester = null;
		customConfigurations = null;
		defaultConfiguration = null;
		return beanTester;
	}
}
