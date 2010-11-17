package org.meanbean.factories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.WeakHashMap;

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
public class CollectionFactoryPluginTest {

	public static final Class<?>[] FACTORY_CLASSES = { List.class, ArrayList.class, LinkedList.class, Map.class,
	        HashMap.class, IdentityHashMap.class, LinkedHashMap.class, TreeMap.class, WeakHashMap.class, Set.class,
	        HashSet.class, LinkedHashSet.class, TreeSet.class };

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
		CollectionFactoryPlugin plugin = new CollectionFactoryPlugin();
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