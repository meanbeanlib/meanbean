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

import com.github.meanbeanlib.mirror.Executables;
import com.github.meanbeanlib.mirror.SerializableLambdas.SerializableFunction1;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryLookup;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class BeanTesterBuilder {

	private int defaultIterations = BeanTester.TEST_ITERATIONS_PER_BEAN;

	private RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();

	private FactoryCollection factoryCollection = FactoryCollection.getInstance();

	private FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();

	private BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	private BeanPropertyTester beanPropertyTester = new BeanPropertyTester();

	private Map<Class<?>, Configuration> customConfigurations = new ConcurrentHashMap<>();

	private Configuration defaultConfiguration;

	public static BeanTesterBuilder newBeanTesterBuilder() {
		return new BeanTesterBuilder();
	}

	public static BeanTester newBeanTester() {
		return new BeanTesterBuilder().build();
	}

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

	/**
	 * Register a custom factory for given class
	 */
	public <T> BeanTesterBuilder registerFactory(Class<T> clazz, Factory<? extends T> factory) {
		getFactoryCollection().addFactory(clazz, factory);
		return this;
	}

	/**
	 * Register factory for an inheritance type hierarchy
	 */
	public <T> BeanTesterBuilder registerTypeHierarchyFactory(Class<T> baseType, Factory<T> factory) {
		getFactoryCollection().addFactoryLookup(new FactoryLookup() {

			@Override
			public boolean hasFactory(Type type) throws IllegalArgumentException {
				return type instanceof Class && baseType.isAssignableFrom((Class<?>) type);
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

	public int getDefaultIterations() {
		return defaultIterations;
	}

	/**
	 * Set the number of times a type should be tested by default
	 */
	public BeanTesterBuilder setDefaultIterations(int iterations) {
		ValidationHelper.ensure(iterations >= 1, "Iterations must be at least 1.");
		this.defaultIterations = iterations;
		return this;
	}

	public int getIterations(Class<?> beanClass) {
		return getConfigurationFor(beanClass).getIterations();
	}

	public BeanTesterBuilder setIterations(Class<?> beanClass, int num) {
		getConfigurationFor(beanClass).setIterations(num);
		return this;
	}

	/**
	 * Mark the specified property as one to be disregarded/ignored during testing.
	 */
	public BeanTesterBuilder addIgnoredPropertyName(Class<?> beanClass, String property) throws IllegalArgumentException {
		ValidationHelper.ensureExists("property", "add property to ignored properties collection", property);
		getConfigurationFor(beanClass).getIgnoredProperties().add(property);
		return this;
	}

	/**
	 * Mark the specified property as one to be disregarded/ignored during testing.
	 * <pre>
	 *     addIgnoredProperty(MyBean.class, MyBean::getPropertyValue);
	 * </pre>
	 */
	public <T, S> BeanTesterBuilder addIgnoredProperty(Class<T> beanClass, SerializableFunction1<T, S> beanGetter)
			throws IllegalArgumentException {
		String propertyName = findPropertyName(beanClass, beanGetter);
		return addIgnoredPropertyName(beanClass, propertyName);
	}

	/**
	 * Register the specified Factory as an override Factory for the specified property. This means that the specified
	 * Factory will be used over the standard Factory for the property.
	 */
	public <T> BeanTesterBuilder addOverrideFactory(Class<T> beanClass, String property, Factory<T> factory)
			throws IllegalArgumentException {
		ValidationHelper.ensureExists("beanClass", "add override Factory", beanClass);
		ValidationHelper.ensureExists("property", "add override Factory", property);
		ValidationHelper.ensureExists("factory", "add override Factory", factory);
		getConfigurationFor(beanClass).getOverrideFactories().put(property, factory);
		return this;
	}

	/**
	 * Register the specified Factory as an override Factory for the specified property. This means that the specified
	 * Factory will be used over the standard Factory for the property.
	 * <pre>
	 *     addOverridePropertyFactory(MyBean.class, MyBean::getPropertyValue, () -&gt; createPropertyValue());
	 * </pre>		
	 */
	public <T, S> BeanTesterBuilder addOverridePropertyFactory(Class<T> beanClass, SerializableFunction1<T, S> beanGetter,
			Factory<S> factory)
			throws IllegalArgumentException {
		ValidationHelper.ensureExists("beanClass", "add override Factory", beanClass);
		ValidationHelper.ensureExists("beanGetter", "add override Factory", beanGetter);
		ValidationHelper.ensureExists("factory", "add override Factory", factory);

		String propertyName = findPropertyName(beanClass, beanGetter);

		getConfigurationFor(beanClass).getOverrideFactories().put(propertyName, factory);
		return this;
	}

	private <T, S> String findPropertyName(Class<T> beanClass, SerializableFunction1<T, S> beanGetter) {
		Method method = Executables.findGetter(beanGetter);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor property = Stream.of(beanInfo.getPropertyDescriptors())
					.filter(pd -> method.equals(pd.getReadMethod()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("invalid bean getter method:" + method));
			return property.getName();
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) e;
			}
			throw new IllegalArgumentException("invalid bean getter method: " + method, e);
		}
	}

	private Configuration getConfigurationFor(Class<?> clazz) {
		return customConfigurations.computeIfAbsent(clazz,
				key -> new Configuration(defaultIterations, new HashSet<>(), new HashMap<>()));
	}

	public BeanTester build() {
		BeanTester beanTester = new BeanTester(
				randomValueGenerator,
				factoryCollection,
				factoryLookupStrategy,
				beanInformationFactory,
				beanPropertyTester,
				customConfigurations,
				defaultConfiguration,
				defaultIterations);

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
