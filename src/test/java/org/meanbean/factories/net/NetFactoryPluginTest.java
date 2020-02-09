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

package org.meanbean.factories.net;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.SimpleFactoryCollection;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(MockitoJUnitRunner.class)
public class NetFactoryPluginTest {

	public static final Class<?>[] FACTORY_CLASSES = { URI.class, URL.class };

	private RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	private FactoryCollection factoryCollection;

	@Before
	public void before() {
		factoryCollection = new SimpleFactoryCollection();
	}

	@Test
	public void shouldRegisterCollectionFactories() throws Exception {
		NetFactoryPlugin plugin = new NetFactoryPlugin();
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
	public void testUrl() {
		NetFactoryPlugin plugin = new NetFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGenerator);

		Factory<?> factory = factoryCollection.getFactory(URL.class);
		URL val1 = (URL) factory.create();
		URL val2 = (URL) factory.create();
		assertThat(val1.toString(), is(not(val2.toString())));
	}

	@Test
	public void testUri() {
		NetFactoryPlugin plugin = new NetFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGenerator);

		Factory<?> factory = factoryCollection.getFactory(URI.class);
		URI val1 = (URI) factory.create();
		URI val2 = (URI) factory.create();
		assertThat(val1, is(not(val2)));
	}
}
