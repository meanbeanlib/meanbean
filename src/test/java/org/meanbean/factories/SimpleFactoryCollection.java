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

	private final Map<Class<?>, Factory<?>> factories = new HashMap<Class<?>, Factory<?>>();

	public boolean hasFactory(Class<?> clazz) throws IllegalArgumentException {
		return factories.containsKey(clazz);
	}

	public Factory<?> getFactory(Class<?> clazz) throws IllegalArgumentException, NoSuchFactoryException {
		return factories.get(clazz);
	}

	public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		factories.put(clazz, factory);
	}
}