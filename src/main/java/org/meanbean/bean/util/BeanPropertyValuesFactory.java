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

package org.meanbean.bean.util;

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.Configuration;
import org.meanbean.util.ValidationHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Factory that creates a <code>Map</code> of values, keyed by property name, that could be used to populate
 * the properties of a Bean, based on information about the Bean provided in the form of a <code>BeanInformation</code>
 * instance.
 * 
 * @author Graham Williamson
 */
public class BeanPropertyValuesFactory implements Factory<Map<String, Object>> {

	/** Information of the object to create. */
	private final BeanInformation beanInformation;

	/** A means of acquiring a suitable Factory for use when creating values. */
	private final FactoryLookupStrategy factoryLookupStrategy;
	
	/** Provides override configuration */
	private final Configuration configuration;

	/**
     * Construct a new Bean Property Values Factory.
     * 
     * @param beanInformation
     *            Information used to create property values for a bean.
     * @param factoryLookupStrategy
     *            Provides a means of acquiring a suitable Factory for use when creating values.
     * @param configuration
     *            Provides override configuration
     * @throws IllegalArgumentException
     *             If the specified BeanInformation or FactoryLookupStrategy is deemed illegal. For example, if either
     *             is <code>null</code>.
     */
    public BeanPropertyValuesFactory(BeanInformation beanInformation, FactoryLookupStrategy factoryLookupStrategy,
            Configuration configuration) {
        ValidationHelper.ensureExists("beanInformation", "populate bean", beanInformation);
        ValidationHelper.ensureExists("factoryLookupStrategy", "populate bean", factoryLookupStrategy);
        ValidationHelper.ensureExists("configuration", "populate bean", configuration);
        this.beanInformation = beanInformation;
        this.factoryLookupStrategy = factoryLookupStrategy;
        this.configuration = configuration;
	}

	/**
	 * Create a Map of values that could be used to populate the properties of the Bean defined by the BeanInformation
	 * provided upon construction of this Factory, keyed by property name.
	 * 
	 * @return A <code>Map</code> of values that could be used to populate the properties of the Bean defined by the
	 *         BeanInformation, keyed by the property name.
	 * 
	 * @throws ObjectCreationException
	 *             If an error occurs when creating the map of values.
	 */
	@Override
    public Map<String, Object> create() throws ObjectCreationException {
		Map<String, Object> propertyValues = new HashMap<String, Object>();
		Collection<PropertyInformation> writableProperties =
				PropertyInformationFilter.filter(beanInformation.getProperties(), PropertyVisibility.WRITABLE);
		writableProperties = PropertyInformationFilter.filter(writableProperties, configuration);
		
		for (PropertyInformation property : writableProperties) {
			String propertyName = property.getName();
			try {
				Factory<?> valueFactory = factoryLookupStrategy.getFactory(beanInformation, property, configuration);
				Object value = valueFactory.create();
				propertyValues.put(propertyName, value);
			} catch (Exception e) {
				String message = "Failed to create a value for property [" + propertyName + "].";
				throw new ObjectCreationException(message, e);
			}
		}
		return propertyValues;
	}
}
