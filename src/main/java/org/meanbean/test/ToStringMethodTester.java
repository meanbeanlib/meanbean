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

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.equivalent.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.util.ServiceFactory;
import org.meanbean.util.ValidationHelper;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Simple tester for verifying that the bean non-trivially overrides the toString method
 */
public class ToStringMethodTester {

	private final Function<Class<?>, Configuration> configurationProvider;
	
	static ToStringMethodTester createWithInheritedContext(Function<Class<?>, Configuration> configurationProvider) {
		return new ToStringMethodTester(ServiceFactory::createContextIfNeeded, configurationProvider);
	}
	
	/**
	 * Prefer {@link BeanVerifier}
	 */
	ToStringMethodTester(Function<Class<?>, Configuration> configurationProvider) {
		this(ServiceFactory::createContext, configurationProvider);
	}

	private ToStringMethodTester(Consumer<ToStringMethodTester> serviceCreator, Function<Class<?>, Configuration> configurationProvider) {
		serviceCreator.accept(this);
		this.configurationProvider = configurationProvider;
	}

	/**
     * Simple toString() test to verify that the bean overrides toString() method and that it does not throw exception.
     */
    public void testToStringMethod(Class<?> beanClass) {
        ValidationHelper.ensureExists("clazz", "test toString method", beanClass);

        FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();
        BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();
        BeanInformation beanInformation = beanInformationFactory.create(beanClass);
        
        Configuration configuration = configurationProvider.apply(beanClass);
        EquivalentPopulatedBeanFactory factory = new EquivalentPopulatedBeanFactory(beanInformation, factoryLookupStrategy, configuration);

        Object bean = factory.create();
        String toString = bean.toString();
        if (!overridesToString(bean, toString)) {
            throw new AssertionError("Expected " + beanClass.getName() + " class to override toString()");
        }
    }

	private boolean overridesToString(Object obj, String toString) {
		String defaultToString = obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
		return !Objects.equals(defaultToString, toString);
	}
}
