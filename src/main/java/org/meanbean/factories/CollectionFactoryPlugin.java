package org.meanbean.factories;

import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.factories.collections.ArrayListFactory;
import org.meanbean.factories.collections.HashMapFactory;
import org.meanbean.factories.collections.HashSetFactory;
import org.meanbean.factories.collections.IdentityHashMapFactory;
import org.meanbean.factories.collections.LinkedHashMapFactory;
import org.meanbean.factories.collections.LinkedHashSetFactory;
import org.meanbean.factories.collections.LinkedListFactory;
import org.meanbean.factories.collections.TreeMapFactory;
import org.meanbean.factories.collections.TreeSetFactory;
import org.meanbean.factories.collections.WeakHashMapFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.WeakHashMap;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create Collection objects.
 * 
 * @author Graham Williamson
 */
public class CollectionFactoryPlugin implements FactoryCollectionPlugin {

	/**
	 * Initialize the plugin, adding Factories to the FactoryCollection.
	 * 
	 * @param factoryCollection
	 *            A FactoryCollection that Factory objects can be added to.
	 * @param randomValueGeneratorProvider
	 *            A RandomValueGeneratorProvider that provides access to a RandomValueGenerator that can be used by
	 *            Factory objects.
	 */
	@Override
    public void initialize(FactoryCollection factoryCollection,
	        RandomValueGeneratorProvider randomValueGeneratorProvider) {
		RandomValueGenerator randomValueGenerator = randomValueGeneratorProvider.getRandomValueGenerator();
		StringFactory stringFactory = (StringFactory) factoryCollection.getFactory(String.class);
		LongFactory longFactory = (LongFactory) factoryCollection.getFactory(Long.class);
		// Lists
		ArrayListFactory<?> arrayListFactory = new ArrayListFactory<String>(randomValueGenerator, stringFactory);
		factoryCollection.addFactory(List.class, arrayListFactory);
		factoryCollection.addFactory(ArrayList.class, arrayListFactory);
		LinkedListFactory<String> linkedListFactory =
		        new LinkedListFactory<String>(randomValueGenerator, stringFactory);
		factoryCollection.addFactory(LinkedList.class, linkedListFactory);
		// Maps
		HashMapFactory<String, Long> hashMapFactory =
		        new HashMapFactory<String, Long>(randomValueGenerator, stringFactory, longFactory);
		factoryCollection.addFactory(Map.class, hashMapFactory);
		factoryCollection.addFactory(HashMap.class, hashMapFactory);
		factoryCollection.addFactory(IdentityHashMap.class, new IdentityHashMapFactory<String, Long>(
		        randomValueGenerator, stringFactory, longFactory));
		factoryCollection.addFactory(LinkedHashMap.class, new LinkedHashMapFactory<String, Long>(randomValueGenerator,
		        stringFactory, longFactory));
		factoryCollection.addFactory(TreeMap.class, new TreeMapFactory<String, Long>(randomValueGenerator,
		        stringFactory, longFactory));
		factoryCollection.addFactory(WeakHashMap.class, new WeakHashMapFactory<String, Long>(randomValueGenerator,
		        stringFactory, longFactory));
		// Sets
		HashSetFactory<String> hashSetFactory = new HashSetFactory<String>(randomValueGenerator, stringFactory);
		factoryCollection.addFactory(Set.class, hashSetFactory);
		factoryCollection.addFactory(HashSet.class, hashSetFactory);
		factoryCollection.addFactory(LinkedHashSet.class, new LinkedHashSetFactory<String>(randomValueGenerator,
		        stringFactory));
		factoryCollection.addFactory(TreeSet.class, new TreeSetFactory<String>(randomValueGenerator, stringFactory));
		// Other
		factoryCollection.addFactory(Collection.class, arrayListFactory);
		factoryCollection.addFactory(Queue.class, linkedListFactory);
		factoryCollection.addFactory(Deque.class, linkedListFactory);
	}
}