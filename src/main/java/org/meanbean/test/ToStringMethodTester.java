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
import org.meanbean.factories.equivalent.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.util.ValidationHelper;

import java.util.Objects;

public class ToStringMethodTester {

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	/**
	 * Simple toString() test to verify that the bean overrides toString() method and that it does not throw exception.
	 */
	public void testToStringMethod(Class<?> clazz) {
		ValidationHelper.ensureExists("clazz", "test hash code method", clazz);
		FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();
		EquivalentPopulatedBeanFactory factory = new EquivalentPopulatedBeanFactory(beanInformationFactory.create(clazz),
				factoryLookupStrategy);

		Object bean = factory.create();
		String toString = bean.toString();
		if (!overridesToString(bean, toString)) {
			throw new AssertionError("Expected " + clazz.getName() + " class to override toString()");
		}
	}

	private boolean overridesToString(Object obj, String toString) {
		String defaultToString = obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
		return !Objects.equals(defaultToString, toString);
	}
}
