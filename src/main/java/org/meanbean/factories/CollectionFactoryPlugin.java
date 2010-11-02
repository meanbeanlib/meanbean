package org.meanbean.factories;

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
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.RandomNumberGeneratorProvider;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create Collection objects.
 * 
 * @author Graham Williamson
 */
public class CollectionFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomNumberGeneratorProvider randomNumberGeneratorProvider) {
		RandomNumberGenerator randomNumberGenerator = randomNumberGeneratorProvider.getRandomNumberGenerator();
		StringFactory stringFactory = (StringFactory) factoryCollection.getFactory(String.class);
		LongFactory longFactory = (LongFactory) factoryCollection.getFactory(Long.class);
		// Lists
		ArrayListFactory<String> arrayListFactory = new ArrayListFactory<String>(randomNumberGenerator, stringFactory);
		factoryCollection.addFactory(List.class, arrayListFactory);
		factoryCollection.addFactory(ArrayList.class, arrayListFactory);
		factoryCollection.addFactory(LinkedList.class, new LinkedListFactory<String>(randomNumberGenerator, stringFactory));		
		// Maps
		HashMapFactory<String, Long> hashMapFactory = new HashMapFactory<String, Long>(randomNumberGenerator, stringFactory,
		        longFactory);
		factoryCollection.addFactory(Map.class, hashMapFactory);
		factoryCollection.addFactory(HashMap.class, hashMapFactory);
		factoryCollection.addFactory(IdentityHashMap.class, new IdentityHashMapFactory<String, Long>(randomNumberGenerator, stringFactory,
		        longFactory));
		factoryCollection.addFactory(LinkedHashMap.class, new LinkedHashMapFactory<String, Long>(randomNumberGenerator, stringFactory,
		        longFactory));
		factoryCollection.addFactory(TreeMap.class, new TreeMapFactory<String, Long>(randomNumberGenerator, stringFactory,
		        longFactory));
		factoryCollection.addFactory(WeakHashMap.class, new WeakHashMapFactory<String, Long>(randomNumberGenerator, stringFactory,
		        longFactory));
		// Sets
		HashSetFactory<String> hashSetFactory = new HashSetFactory<String>(randomNumberGenerator, stringFactory);
		factoryCollection.addFactory(Set.class, hashSetFactory);
		factoryCollection.addFactory(HashSet.class, hashSetFactory);
		factoryCollection.addFactory(LinkedHashSet.class, new LinkedHashSetFactory<String>(randomNumberGenerator, stringFactory));
		factoryCollection.addFactory(TreeSet.class, new TreeSetFactory<String>(randomNumberGenerator, stringFactory));
	}
}