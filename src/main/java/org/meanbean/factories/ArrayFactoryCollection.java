package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;
import org.meanbean.util.RandomValueGenerator;

import java.lang.reflect.Array;

/**
 * FactoryCollection for array types
 */
@Order(5000)
@MetaInfServices
public class ArrayFactoryCollection implements FactoryCollection {

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
	public Factory<?> getFactory(Class<?> clazz) {
		return () -> randomArray(clazz);
	}

	@Override
	public boolean hasFactory(Class<?> clazz) {
		return clazz.isArray();
	}

	private Object randomArray(Class<?> clazz) {
		int length = randomValueGenerator.nextInt(maxSize);
		Factory<?> componentFactory = getComponentFactory(clazz);
		Object array = Array.newInstance(clazz.getComponentType(), length);
		for (int i = 0; i < length; i++) {
			Array.set(array, i, componentFactory.create());
		}
		return array;
	}

	private Factory<?> getComponentFactory(Class<?> clazz) {
		FactoryCollection instance = FactoryCollection.getInstance();
		return instance.getFactory(clazz.getComponentType());
	}
}