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
	private int maxArrayLength = 8;

	public int getMaxArrayLength() {
		return maxArrayLength;
	}

	public void setMaxArrayLength(int maxArrayLength) {
		this.maxArrayLength = maxArrayLength;
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
		int length = randomValueGenerator.nextInt(maxArrayLength);
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