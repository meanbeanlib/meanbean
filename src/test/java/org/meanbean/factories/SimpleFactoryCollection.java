package org.meanbean.factories;

import org.meanbean.lang.Factory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple concrete implementation of the FactoryCollection interface that should be used for testing only.
 * 
 * @author Graham Williamson
 */
public class SimpleFactoryCollection implements FactoryCollection {

	private final Map<Class<?>, Factory<?>> factories = new HashMap<Class<?>, Factory<?>>();

	@Override
    public boolean hasFactory(Type type) throws IllegalArgumentException {
		return factories.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	@Override
    public <T> Factory<T> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
		return (Factory<T>) factories.get(type);
	}

	@Override
    public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		factories.put(clazz, factory);
	}

	@Override
	public void addFactoryLookup(FactoryLookup factoryLookup) {
		throw new UnsupportedOperationException();
	}
}