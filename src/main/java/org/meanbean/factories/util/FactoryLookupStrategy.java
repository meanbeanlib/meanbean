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

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTestException;
import org.meanbean.test.Configuration;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceDefinition;

/**
 * Defines a means of acquiring a Factory in the context of testing bean properties
 * 
 * @author Graham Williamson
 */
public interface FactoryLookupStrategy {

	/**
	 * <p>
	 * Get a factory for the specified property that is of the specified type. <br>
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
	 *            Information about the property.
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
	Factory<?> getFactory(BeanInformation beanInformation, PropertyInformation propertyInformation,
			Configuration configuration) throws IllegalArgumentException, BeanTestException;

	public static ServiceDefinition<FactoryLookupStrategy> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryLookupStrategy.class,
				new Class<?>[] { FactoryCollection.class, RandomValueGenerator.class },
				new Object[] { FactoryCollection.getInstance(), RandomValueGenerator.getInstance() });
	}

	public static FactoryLookupStrategy getInstance() {
		return getServiceDefinition().getServiceFactory().getFirst();
	}

}
