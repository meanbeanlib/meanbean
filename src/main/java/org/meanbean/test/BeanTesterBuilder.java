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

import com.github.meanbeanlib.mirror.SerializableLambdas.SerializableFunction1;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryLookup;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.meanbean.util.PropertyNameFinder.findPropertyName;

public class BeanTesterBuilder {

	private RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();

	private FactoryCollection factoryCollection = FactoryCollection.getInstance();

	private FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();

	private BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	private BeanPropertyTester beanPropertyTester = new BeanPropertyTester();

	private Map<Class<?>, Configuration> customConfigurations = new ConcurrentHashMap<>();

	private Configuration defaultConfiguration = new Configuration(BeanTester.TEST_ITERATIONS_PER_BEAN,
			Collections.emptySet(), Collections.emptyMap());

	public static BeanTesterBuilder newBeanTesterBuilder() {
		return new BeanTesterBuilder();
	}

	public static BeanTester newBeanTester() {
		return new BeanTesterBuilder().build();
	}

	public static EqualsMethodTester newEqualsMethodTester() {
		return new BeanTesterBuilder().buildEqualsMethodTester();
	}

	public static HashCodeMethodTester newHashCodeMethodTester() {
		return new HashCodeMethodTester();
	}

	public static ToStringMethodTester newToStringMethodTester() {
		return new ToStringMethodTester();
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
		return defaultConfiguration.getIterations();
	}

	/**
	 * Set the number of times a type should be tested by default
	 */
	public BeanTesterBuilder setDefaultIterations(int iterations) {
		this.defaultConfiguration.setIterations(iterations);
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

	/**
	 * Add a property that is insignificant for EqualsMethodTester
	 */
	public <T, S> BeanTesterBuilder addEqualsInsignificantProperty(Class<T> beanClass, String propertyName) {
		ValidationHelper.ensureExists("beanClass", "add equals insignificantProperty", beanClass);
		ValidationHelper.ensureExists("propertyName", "add equals insignificantProperty", propertyName);
		getConfigurationFor(beanClass).getEqualsInsignificantProperties().add(propertyName);
		return this;
	}

	/**
	 * Add a property that is insignificant for EqualsMethodTester
	 * 
	 * <pre>
	 *     addEqualsInsignificantProperty(MyBean.class, MyBean::getPropertyValue);
	 * </pre>		
	 */
	public <T, S> BeanTesterBuilder addEqualsInsignificantProperty(Class<T> beanClass, SerializableFunction1<T, S> beanGetter) {
		ValidationHelper.ensureExists("beanClass", "add equals insignificantProperty", beanClass);
		ValidationHelper.ensureExists("beanGetter", "add equals insignificantProperty", beanGetter);

		String propertyName = findPropertyName(beanClass, beanGetter);
		return addEqualsInsignificantProperty(beanClass, propertyName);
	}

	private Configuration getConfigurationFor(Class<?> clazz) {
		return customConfigurations.computeIfAbsent(clazz,
				key -> new Configuration(defaultConfiguration.getIterations(), new HashSet<>(), new HashMap<>()));
	}

	public BeanTester build() {
		return new BeanTester(
				randomValueGenerator,
				factoryCollection,
				factoryLookupStrategy,
				beanInformationFactory,
				beanPropertyTester,
				customConfigurations,
				defaultConfiguration);
	}

	public EqualsMethodTester buildEqualsMethodTester() {
		return new EqualsMethodTester(customConfigurations, defaultConfiguration);
	}

	public HashCodeMethodTester buildHashCodeMethodTester() {
		return newHashCodeMethodTester();
	}

	public ToStringMethodTester buildToStringMethodTester() {
		return newToStringMethodTester();
	}

}
