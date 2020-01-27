package org.meanbean.factories.net;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.SimpleFactoryCollection;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NetFactoryPluginTest {

	public static final Class<?>[] FACTORY_CLASSES = { URI.class, URL.class };

	@Mock
	private RandomValueGeneratorProvider randomValueGeneratorProvider;

	private RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	private FactoryCollection factoryCollection;

	@Before
	public void before() {
		when(randomValueGeneratorProvider.getRandomValueGenerator())
				.thenReturn(randomValueGenerator);
		factoryCollection = new SimpleFactoryCollection();
	}

	@Test
	public void shouldRegisterCollectionFactories() throws Exception {
		NetFactoryPlugin plugin = new NetFactoryPlugin();
		for (Class<?> clazz : FACTORY_CLASSES) {
			assertThat("Factory for class [" + clazz + "] should not be registered prior to plugin initialization.",
					factoryCollection.hasFactory(clazz), is(false));
		}
		plugin.initialize(factoryCollection, randomValueGeneratorProvider);
		for (Class<?> clazz : FACTORY_CLASSES) {
			assertThat("Plugin did not register Factory for class [" + clazz + "].",
					factoryCollection.hasFactory(clazz), is(true));
		}
	}

	@Test
	public void testUrl() {
		NetFactoryPlugin plugin = new NetFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGeneratorProvider);

		Factory<?> factory = factoryCollection.getFactory(URL.class);
		URL val1 = (URL) factory.create();
		URL val2 = (URL) factory.create();
		assertThat(val1.toString(), is(not(val2.toString())));
	}

	@Test
	public void testUri() {
		NetFactoryPlugin plugin = new NetFactoryPlugin();
		plugin.initialize(factoryCollection, randomValueGeneratorProvider);

		Factory<?> factory = factoryCollection.getFactory(URI.class);
		URI val1 = (URI) factory.create();
		URI val2 = (URI) factory.create();
		assertThat(val1, is(not(val2)));
	}
}