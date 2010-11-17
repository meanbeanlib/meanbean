package org.meanbean.factories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrimitiveFactoryPluginTest {

	public static final Class<?>[] FACTORY_CLASSES = { boolean.class, byte.class, short.class, int.class, long.class,
	        float.class, double.class, char.class };

	@Mock
	private RandomValueGeneratorProvider randomValueGeneratorProvider;

	private RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	private FactoryCollection factoryCollection;

	@Before
	public void before() {
		when(randomValueGeneratorProvider.getRandomValueGenerator()).thenReturn(randomValueGenerator);
		factoryCollection = new SimpleFactoryCollection();
		factoryCollection.addFactory(String.class, new StringFactory(randomValueGenerator));
		factoryCollection.addFactory(Long.class, new LongFactory(randomValueGenerator));
	}

	@Test
	public void shouldRegisterCollectionFactories() throws Exception {
		PrimitiveFactoryPlugin plugin = new PrimitiveFactoryPlugin();
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

}