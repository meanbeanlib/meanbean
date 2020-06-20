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

package org.meanbean.factories.util;

import org.kohsuke.MetaInfServices;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.factories.BasicNewObjectInstanceFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.basic.EnumFactory;
import org.meanbean.factories.beans.PopulatedBeanFactory;
import org.meanbean.lang.Factory;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;
import org.meanbean.test.Configuration;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.Types;
import org.meanbean.util.ValidationHelper;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.meanbean.test.Warning.DYNAMICALLY_CREATED_FACTORY;

/**
 * <p>
 * Concrete FactoryLookupStrategy that implements the following Factory lookup algorithm:
 * </p>
 * 
 * <ol>
 * <li>If a Configuration is provided, this is first inspected for a property-specific Factory.</li>
 * 
 * <li>If no Configuration is provided or there is no property-specific Factory in the Configuration, the
 * FactoryCollection is then searched for a Factory suitable for the type of the property.</li>
 * 
 * <li>If the FactoryCollection does not contain a suitable Factory, an attempt is made to create a Factory for the
 * type. For example, if the type is an Enum, then a generic Enum Factory will be created for the Enum's constants,
 * registered in the Factory Collection for future use, and returned from this method. As a last resort, an attempt is
 * made create a Factory that creates objects of the custom data type. If successful, this Factory is registered in the
 * Factory Collection for future use, and return from this method.</li>
 * 
 * <li>If ultimately a suitable Factory cannot be found or created, a NoSuchFactoryException detailing the problem is
 * thrown.</li>
 * </ol>
 * 
 * @author Graham Williamson
 */
@MetaInfServices
public class BasicFactoryLookupStrategy implements FactoryLookupStrategy {

	private static final Set<String> dynamicallyCreatedFactories = ConcurrentHashMap.newKeySet();

	/** Logging mechanism. */
	private static final $Logger logger = $LoggerFactory.getLogger(BasicFactoryLookupStrategy.class);

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator;

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection;

	/**
	 * Construct a new Factory Lookup Strategy.
	 * 
	 * @param factoryCollection
	 *            A collection of test data Factories.
	 * @param randomValueGenerator
	 *            Random number generator used by factories to randomly generate values.
	 * 
	 * @throws IllegalArgumentException
	 *             If either the factoryCollection or randomValueGenerator are deemed illegal. For example, if either is
	 *             <code>null</code>.
	 */
	public BasicFactoryLookupStrategy(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator)
			throws IllegalArgumentException {
		ValidationHelper.ensureExists("factoryCollection", "construct FactoryLookupStrategy", factoryCollection);
		ValidationHelper.ensureExists("randomValueGenerator", "construct FactoryLookupStrategy", randomValueGenerator);
		this.factoryCollection = factoryCollection;
		this.randomValueGenerator = randomValueGenerator;
	}

	/**
	 * <p>
	 * Get a factory for the specified property that is of the specified type. <br>
	 * </p>
	 * 
	 * <p>
	 * If a Configuration is provided, this is first inspected for a property-specific Factory. <br>
	 * </p>
	 * 
	 * <p>
	 * If no Configuration is provided or there is no property-specific Factory in the Configuration, the
	 * FactoryCollection is then searched for a Factory suitable for the type of the property. <br>
	 * </p>
	 * 
	 * <p>
	 * If the FactoryCollection does not contain a suitable Factory, an attempt is made to create a Factory for the
	 * type.
	 * </p>
	 * 
	 * <p>
	 * For example, if the type is an Enum, then a generic Enum Factory will be created for the Enum's constants,
	 * registered in the Factory Collection for future use, and returned from this method. <br>
	 * </p>
	 * 
	 * <p>
	 * As a last resort, an attempt is made create a Factory that creates objects of the custom data type. If
	 * successful, this Factory is registered in the Factory Collection for future use, and return from this method. <br>
	 * </p>
	 * 
	 * <p>
	 * If ultimately a suitable Factory cannot be found or created, a NoSuchFactoryException detailing the problem is
	 * thrown.
	 * </p>
	 * 
	 * @param beanInformation
	 *            Information about the bean the property belongs to.
	 * @param propertyInformation
	 *            The information of the property.
	 * @param configuration
	 *            An optional Configuration object that may contain an override Factory for the specified property. Pass
	 *            <code>null</code> if no Configuration exists.
	 * 
	 * @return A Factory that may be used to create objects appropriate for the specified property.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the required parameters are deemed illegal. For example, if any are null.
	 * @throws NoSuchFactoryException
	 *             If an unexpected exception occurs when getting the Factory, including failing to find a suitable
	 *             Factory.
	 */
	@Override
	public Factory<?> getFactory(BeanInformation beanInformation, PropertyInformation propertyInformation,
			Configuration configuration) throws IllegalArgumentException, NoSuchFactoryException {
		ValidationHelper.ensureExists("beanInformation", "get factory", beanInformation);
		ValidationHelper.ensureExists("propertyInformation", "get factory", propertyInformation);
		return doGetFactory(beanInformation, propertyInformation, configuration);
	}

	private Factory<?> doGetFactory(BeanInformation beanInformation, PropertyInformation propertyInformation,
			Configuration configuration) {

		String propertyName = propertyInformation.getName();
		Type genericType = propertyInformation.getReadMethodReturnType();
		Class<?> propertyType = Types.getRawType(genericType);

		if (propertyHasOverrideFactoryInConfiguration(propertyName, configuration)) {
			return getPropertyOverrideFactoryFromConfiguration(propertyName, configuration);

		} else if (propertyTypeHasRegisteredFactory(propertyInformation)) {
			return getPropertyTypeRegisteredFactory(propertyInformation);

		} else if (propertyIsAnEnum(propertyType)) {
			return getAndCachePropertyEnumFactory(propertyType);

		} else if (propertyIsNotTheSameTypeAsItsParent(beanInformation, propertyType)) {
			return createTestedPopulatedBeanFactory(beanInformation, propertyName, propertyType, configuration);

		} else {
			return createTestedUnpopulatedBeanFactory(beanInformation, propertyName, propertyType, configuration);
		}
	}

	private boolean propertyHasOverrideFactoryInConfiguration(String propertyName, Configuration configuration) {
		return (configuration != null) && (configuration.hasOverrideFactory(propertyName));
	}

	private Factory<?> getPropertyOverrideFactoryFromConfiguration(String propertyName, Configuration configuration) {
		return configuration.getOverrideFactory(propertyName);
	}

	private boolean propertyTypeHasRegisteredFactory(PropertyInformation propertyType) {
		Type genericPropertyType = propertyType.getReadMethodReturnType();
		return factoryCollection.hasFactory(genericPropertyType);
	}

	private Factory<?> getPropertyTypeRegisteredFactory(PropertyInformation propertyType) {
		Type genericPropertyType = propertyType.getReadMethodReturnType();
		return factoryCollection.getFactory(genericPropertyType);
	}

	private boolean propertyIsAnEnum(Class<?> propertyType) {
		return propertyType.isEnum();
	}

	private Factory<?> getAndCachePropertyEnumFactory(Class<?> propertyType) {
		EnumFactory factory = getEnumPropertyFactory(propertyType);
		cacheEnumPropertyFactory(propertyType, factory);
		return factory;
	}

	private EnumFactory getEnumPropertyFactory(Class<?> propertyType) {
		return new EnumFactory(propertyType, randomValueGenerator);
	}

	private void cacheEnumPropertyFactory(Class<?> propertyType, EnumFactory enumFactory) {
		factoryCollection.addFactory(propertyType, enumFactory);
	}

	private boolean propertyIsNotTheSameTypeAsItsParent(BeanInformation beanInformation, Class<?> propertyType) {
		return !propertyType.equals(beanInformation.getBeanClass());
	}

	private Factory<?> createTestedPopulatedBeanFactory(BeanInformation beanInformation, String propertyName,
			Class<?> propertyType, Configuration configuration) {
		try {
			onDynamicFactoryCreation(beanInformation, propertyName, propertyType, configuration);

			Factory<?> populatedBeanFactory = createPopulatedBeanFactory(propertyType);
			testPopulatedBeanFactory(populatedBeanFactory);
			return populatedBeanFactory;
		} catch (Exception e) {
			String message = "Failed to find suitable Factory for property=[" + propertyName + "] of type=[" + propertyType
					+ "]. Please register a custom Factory.";
			throw new NoSuchFactoryException(message, e);
		}
	}

	private void onDynamicFactoryCreation(BeanInformation beanInformation, String propertyName, Class<?> propertyType,
			Configuration configuration) {
		if (isLikelyNewDynamicallyCreatedFactoryType(beanInformation, propertyType, configuration)) {
			// TODO THIS IS WHERE A STRICTER VERSION COULD THROW AN EXCEPTION

			// To meanbean users, this can usually be ignored. When meanbean finds a property type that does not have built-in
			// support for creating random values (like it does with String, Date, etc), then meanbean creates a dynamic factory
			// hoping that the property type is a java bean. That dynamic factory is used to create random values of the property.
			//
			// To register a custom factory, call VerifierSettings::registerFactory or suppress with Warning.DYNAMICALLY_CREATED_FACTORY
			logger.info("Using dynamically created factory for [{}] of type [{}] because a custom Factory cannot be found.",
					propertyName, propertyType.getName());
		}
	}
	
	// cache the info so that warning logs don't appear repeatedly
	private boolean isLikelyNewDynamicallyCreatedFactoryType(BeanInformation beanInformation, Class<?> propertyType, Configuration configuration) {
		if (configuration != null && configuration.isSuppressedWarning(DYNAMICALLY_CREATED_FACTORY)) {
			return false;
		}
		
		if (dynamicallyCreatedFactories.size() > 1000) {
			synchronized (dynamicallyCreatedFactories) {
				// trim the cache size
				dynamicallyCreatedFactories.removeIf(value -> dynamicallyCreatedFactories.size() > 50);
			}
		}
		String key = beanInformation.getBeanClass().getName() + "." + propertyType.getName();
		return dynamicallyCreatedFactories.add(key);
	}

	private Factory<?> createPopulatedBeanFactory(Class<?> propertyType) {
		BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();
		BeanInformation propertyBeanInformation = beanInformationFactory.create(propertyType);
		return new PopulatedBeanFactory(propertyBeanInformation, this);
	}

	private void testPopulatedBeanFactory(Factory<?> equivalentPopulatedBeanFactory) {
		equivalentPopulatedBeanFactory.create();
	}

	private Factory<?> createTestedUnpopulatedBeanFactory(BeanInformation beanInformation, String propertyName,
			Class<?> propertyType, Configuration configuration) {
		try {
			onDynamicFactoryCreation(beanInformation, propertyName, propertyType, configuration);
			
			Factory<?> unpopulatedBeanFactory = createUnpopulatedBeanFactory(propertyType);
			testUnpopulatedBeanFactory(unpopulatedBeanFactory);
			return unpopulatedBeanFactory;
		} catch (Exception e) {
			String message = "Failed to find suitable Factory for property=[" + propertyName + "] of type=[" + propertyType
					+ "]. Please register a custom Factory.";
			throw new NoSuchFactoryException(message, e);
		}
	}

	private Factory<?> createUnpopulatedBeanFactory(Class<?> propertyType) {
		return BasicNewObjectInstanceFactory.findBeanFactory(propertyType);
	}

	private void testUnpopulatedBeanFactory(Factory<?> basicFactory) {
		basicFactory.create();
	}
}
