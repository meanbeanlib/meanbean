package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;
import org.meanbean.util.RandomValueGenerator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static org.meanbean.util.Types.getRawType;

/**
 * FactoryCollection for java.util.Collection types
 */
@Order(4000)
@MetaInfServices
public class CollectionFactoryCollection implements FactoryCollection {

	private final RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();
	private int maxSize = 8;

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxArrayLength) {
		this.maxSize = maxArrayLength;
	}

	@Override
	public void addFactory(Class<?> clazz, Factory<?> factory) {

	}

	@Override
	public Factory<?> getFactory(Type typeToken) throws IllegalArgumentException, NoSuchFactoryException {
		return createCollectionPopulatingFactory(typeToken);
	}

	private Factory<?> findItemFactory(Type itemType) {
		FactoryCollection factoryCollection = FactoryCollection.getInstance();
		try {
			return factoryCollection.getFactory(itemType);
		} catch (NoSuchFactoryException e) {
			return factoryCollection.getFactory(void.class);
		}
	}

	@Override
	public boolean hasFactory(Type type) {
		Class<?> clazz = org.meanbean.util.Types.getRawType(type);
		return !clazz.equals(void.class) && (Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz));
	}

	private Type findElementType(Type type, int index) {
		if (type instanceof ParameterizedType) {
			return ((ParameterizedType) type).getActualTypeArguments()[index];
		}
		return String.class;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Factory<?> createCollectionPopulatingFactory(Type typeToken) {
		Class<?> rawType = getRawType(typeToken);
		Factory<Object> instanceFactory = findCollectionInstanceFactory(rawType);

		Type itemType = findElementType(typeToken, 0);
		Factory<?> itemFactory = findItemFactory(itemType);

		if (Map.class.isAssignableFrom(rawType)) {
			Type valueType = findElementType(typeToken, 1);
			Factory<?> valueFactory = findItemFactory(valueType);

			Factory<Object> populatingFactory = () -> {
				Map map = (Map) instanceFactory.create();

				int size = randomValueGenerator.nextInt(maxSize);
				for (int idx = 0; idx < size; idx++) {
					map.put(itemFactory.create(), valueFactory.create());
				}
				return map;
			};

			return populatingFactory;

		} else {
			Factory<Object> populatingFactory = () -> {
				Collection collection = (Collection) instanceFactory.create();

				int size = randomValueGenerator.nextInt(maxSize);
				for (int idx = 0; idx < size; idx++) {
					collection.add(itemFactory.create());
				}
				return collection;
			};

			return populatingFactory;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> Factory<T> findCollectionInstanceFactory(Class<?> rawType) {

		Map<Class<?>, Factory<?>> collectionFactories = new HashMap<>();

		// Lists
		collectionFactories.put(List.class, ArrayList::new);

		// Maps
		collectionFactories.put(Map.class, HashMap::new);

		// Sets
		collectionFactories.put(Set.class, HashSet::new);
		
		// Other
		collectionFactories.put(Collection.class, ArrayList::new);
		collectionFactories.put(Queue.class, LinkedList::new);
		collectionFactories.put(Deque.class, LinkedList::new);

		Factory<?> factory = collectionFactories.get(rawType);
		if (factory == null) {
			factory = () -> {
				try {
					return rawType.getConstructor().newInstance();
				} catch (Exception e) {
					throw new IllegalStateException("cannot create instance for " + rawType, e);
				}
			};
		}
		return (Factory<T>) factory;
	}
}