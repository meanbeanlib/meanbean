package org.meanbean.factories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.RandomNumberGeneratorProvider;
import org.meanbean.util.SimpleRandomNumberGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrimitiveFactoryPluginTest {

	public static final Class<?>[] FACTORY_CLASSES = { boolean.class, byte.class, short.class, int.class, long.class,
	        float.class, double.class, char.class };

	@Mock
	private RandomNumberGeneratorProvider randomNumberGeneratorProvider;

	private RandomNumberGenerator randomNumberGenerator = new SimpleRandomNumberGenerator();

	private FactoryCollection factoryCollection;

	@Before
	public void before() {
		when(randomNumberGeneratorProvider.getRandomNumberGenerator()).thenReturn(randomNumberGenerator);
		factoryCollection = new SimpleFactoryCollection();
		factoryCollection.addFactory(String.class, new StringFactory(randomNumberGenerator));
		factoryCollection.addFactory(Long.class, new LongFactory(randomNumberGenerator));
	}

	@Test
	public void shouldRegisterCollectionFactories() throws Exception {
		PrimitiveFactoryPlugin plugin = new PrimitiveFactoryPlugin();
		for (Class<?> clazz : FACTORY_CLASSES) {
			assertThat("Factory for class [" + clazz + "] should not be registered prior to plugin initialization.",
			        factoryCollection.hasFactory(clazz), is(false));
		}
		plugin.initialize(factoryCollection, randomNumberGeneratorProvider);
		for (Class<?> clazz : FACTORY_CLASSES) {
			assertThat("Plugin did not register Factory for class [" + clazz + "].",
			        factoryCollection.hasFactory(clazz), is(true));
		}
	}

}