package org.meanbean.factories;

import java.util.HashMap;
import java.util.Map;

import org.meanbean.lang.Factory;

/**
 * Simple concrete implementation of the FactoryCollection interface that should be used for testing only.
 * 
 * @author Graham Williamson
 */
public class SimpleFactoryCollection implements FactoryCollection {

	private Map<Class<?>, Factory<?>> factories = new HashMap<Class<?>, Factory<?>>();

	@Override
	public boolean hasFactory(Class<?> clazz) throws IllegalArgumentException {
		return factories.containsKey(clazz);
	}

	@Override
	public Factory<?> getFactory(Class<?> clazz) throws IllegalArgumentException, NoSuchFactoryException {
		return factories.get(clazz);
	}

	@Override
	public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		factories.put(clazz, factory);
	}
}