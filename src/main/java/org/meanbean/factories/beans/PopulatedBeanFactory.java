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

package org.meanbean.factories.beans;

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.util.BasicBeanPopulator;
import org.meanbean.bean.util.BeanPopulator;
import org.meanbean.bean.util.BeanPropertyValuesFactory;
import org.meanbean.factories.BasicNewObjectInstanceFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.ValidationHelper;

import java.util.Map;

/**
 * Factory that creates object instances based on provided BeanInformation, assigning each instance different values.
 * 
 * @author Graham Williamson
 */
public class PopulatedBeanFactory implements Factory<Object> {

	/** The BeanInformation that should be used to create instances of a bean. */
	private final BeanInformation beanInformation;

	/** Creates values that can be used to populate the properties of a Bean. */
	private final BeanPropertyValuesFactory beanPropertyValuesFactory;

	/** Affords functionality to populate a bean (set its fields) with specified values. */
	private final BeanPopulator beanPopulator = new BasicBeanPopulator();

	/**
	 * Construct a new Factory that creates object instances based on provided BeanInformation, assigning each instance
	 * different field values.
	 * 
	 * @param beanInformation
	 *            Information used to create instances of a bean.
	 * @param factoryLookupStrategy
	 *            Provides a means of acquiring Factories that can be used to create values for the fields of new object
	 *            instances.
	 * @throws IllegalArgumentException
	 *             If either the specified BeanInformation or the FactoryLookupStrategy is deemed illegal. For example,
	 *             if either is <code>null</code>.
	 */
	public PopulatedBeanFactory(BeanInformation beanInformation, FactoryLookupStrategy factoryLookupStrategy)
	        throws IllegalArgumentException {
		ValidationHelper.ensureExists("beanInformation", "construct Factory", beanInformation);
		ValidationHelper.ensureExists("factoryLookupStrategy", "construct Factory", factoryLookupStrategy);
		this.beanInformation = beanInformation;
		beanPropertyValuesFactory = new BeanPropertyValuesFactory(beanInformation, factoryLookupStrategy);
	}

	/**
	 * Create a new instance of the Bean described in the provided BeanInformation.
	 * 
	 * @throws BeanCreationException
	 *             If an error occurs when creating an instance of the Bean.
	 */
	@Override
    public Object create() throws BeanCreationException {
		Map<String, Object> propertyValues = beanPropertyValuesFactory.create();
		Factory<Object> beanFactory = BasicNewObjectInstanceFactory.findBeanFactory(beanInformation.getBeanClass());
		Object result = beanFactory.create();
		beanPopulator.populate(result, beanInformation, propertyValues);
		return result;
	}
}
