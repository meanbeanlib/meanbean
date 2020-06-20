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

import org.apache.commons.lang3.ClassUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceFactory;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Type;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(MockitoJUnitRunner.class)
public class FactoryRepositoryTest {

	@Mock
	private Factory<?> mockFactory;

	@Mock
	private RandomValueGenerator randomValueGenerator;

	private FactoryRepository factoryRepository;

	@Before
	public void before() {
		ServiceFactory.createContext(this);
		factoryRepository = new FactoryRepository();
	}

	@Test
	public void constructorShouldRegisterPrimitiveFactoriesWithFactoryRepository() throws Exception {
		for (Class<?> clazz : PrimitiveFactoryPluginTest.FACTORY_CLASSES) {
			assertThat("FactoryRepository did not register primitive Factory for class [" + clazz + "].",
			        factoryRepository.hasFactory(clazz), is(true));
		}
	}

	@Test
	public void constructorShouldRegisterObjectFactoriesWithFactoryRepository() throws Exception {
		for (Class<?> clazz : ObjectFactoryPluginTest.FACTORY_CLASSES) {
			assertThat("FactoryRepository did not register object Factory for class [" + clazz + "].",
			        factoryRepository.hasFactory(clazz), is(true));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFactoryShouldPreventNullClass() throws Exception {
		factoryRepository.addFactory(null, mockFactory);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFactoryShouldPreventNullFactory() throws Exception {
		factoryRepository.addFactory(String.class, null);
	}

	@Test
	public void addFactoryShouldRegisterFactoryInFactoryRepository() throws Exception {
		Factory<?> factory = new RegisteredFactory();
		assertThat("Factory should not be registered yet.", factoryRepository.hasFactory(RegisteredTestClass.class),
		        is(false));
		factoryRepository.addFactory(RegisteredTestClass.class, factory);
		assertThat("Incorrect factory found.", factoryRepository.getFactory(RegisteredTestClass.class),
		        is(instanceOf(RegisteredFactory.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFactoryShouldPreventNullClass() throws Exception {
		factoryRepository.getFactory((Class<?>) null);
	}

	@Test(expected = NoSuchFactoryException.class)
	public void getFactoryShouldThrowExceptionForUnregisteredFactory() throws Exception {
		factoryRepository.getFactory(UnregisteredTestClass.class);
	}

	@Test
	public void getFactoryShouldReturnRegisteredFactory() throws Exception {
		assertThat("Should find factory.", factoryRepository.getFactory(String.class),
		        is(instanceOf(StringFactory.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void hasFactoryShouldPreventNullClass() throws Exception {
		factoryRepository.hasFactory((Class<?>) null);
	}

	@Test
	public void hasFactoryShouldReturnFalseForUnregisteredFactory() throws Exception {
		assertThat("Should not find any factory.", factoryRepository.hasFactory(UnregisteredTestClass.class), is(false));
	}

	@Test
	public void hasFactoryShouldReturnTrueForRegisteredFactory() throws Exception {
		assertThat("Should find factory.", factoryRepository.hasFactory(String.class), is(true));
	}

	@Test
	public void factoriesCreateTypeMatchingInstances() {
		Map<Type, Factory<?>> factories = factoryRepository.getFactories();
		for (Map.Entry<Type, Factory<?>> entry : factories.entrySet()) {
			Class<?> key = (Class<?>) entry.getKey();
			Factory<?> factory = entry.getValue();

			if (key.equals(void.class)) {
				assertThat(factory.create(), is(nullValue()));
				continue;
			}
			
			if (ClassUtils.isPrimitiveOrWrapper(key) && !ClassUtils.isPrimitiveWrapper(key)) {
				key = (Class<?>) ClassUtils.primitiveToWrapper(key);
			}

			Object instance = factory.create();
			assertThat(instance, instanceOf(key));
		}
	}

	static class RegisteredTestClass {
		// A class that will be registered in the FactoryRepository
	}

	static class UnregisteredTestClass {
		// A class that has no Factory registered against it in the FactoryRepository
	}

	static class RegisteredFactory implements Factory<RegisteredTestClass> {
		@Override
        public RegisteredTestClass create() {
			return null; // Not tested here - do nothing
		}
	}
}
