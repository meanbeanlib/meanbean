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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(MockitoJUnitRunner.class)
public class ConcurrentFactoryPluginTest {

	public static final Class<?>[] FACTORY_CLASSES = { AtomicBoolean.class, AtomicInteger.class, AtomicLong.class };

	private RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	private FactoryCollection factoryCollection;

	@Before
	public void before() {
		factoryCollection = new SimpleFactoryCollection();
	}

	@Test
	public void shouldRegisterCollectionFactories() throws Exception {
		ConcurrentFactoryPlugin plugin = new ConcurrentFactoryPlugin();
		for (Class<?> clazz : FACTORY_CLASSES) {
			assertThat("Factory for class [" + clazz + "] should not be registered prior to plugin initialization.",
					factoryCollection.hasFactory(clazz), is(false));
		}
		plugin.initialize(factoryCollection, randomValueGenerator);
		for (Class<?> clazz : FACTORY_CLASSES) {
			assertThat("Plugin did not register Factory for class [" + clazz + "].",
					factoryCollection.hasFactory(clazz), is(true));
		}
	}

	@Test
	public void testAtomicBoolean() {
		ConcurrentFactoryPlugin plugin = new ConcurrentFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGenerator);

		Factory<?> factory = factoryCollection.getFactory(AtomicBoolean.class);
		AtomicBoolean val1 = (AtomicBoolean) factory.create();
		AtomicBoolean val2 = (AtomicBoolean) factory.create();
		assertThat(val1, is(not(val2)));
	}

	@Test
	public void testAtomicInteger() {
		ConcurrentFactoryPlugin plugin = new ConcurrentFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGenerator);

		Factory<?> factory = factoryCollection.getFactory(AtomicInteger.class);
		AtomicInteger val1 = (AtomicInteger) factory.create();
		AtomicInteger val2 = (AtomicInteger) factory.create();
		assertThat(val1, is(not(val2)));
	}

	@Test
	public void testAtomicLong() {
		ConcurrentFactoryPlugin plugin = new ConcurrentFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGenerator);

		Factory<?> factory = factoryCollection.getFactory(AtomicLong.class);
		AtomicLong val1 = (AtomicLong) factory.create();
		AtomicLong val2 = (AtomicLong) factory.create();
		assertThat(val1, is(not(val2)));
	}
}
