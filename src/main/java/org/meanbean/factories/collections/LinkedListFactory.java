package org.meanbean.factories.collections;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

import java.util.LinkedList;
import java.util.List;

/**
 * Factory that creates random LinkedLists of objects of the specified type.
 * 
 * @author Graham Williamson
 * 
 * @param <T>
 *            The data type of the object this Factory creates LinkedLists of.
 */
public class LinkedListFactory<T> extends ListFactoryBase<T> {

	/**
	 * Construct a new LinkedList object Factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * @param itemFactory
	 *            Factory used to create each LinkedList item.
	 */
	public LinkedListFactory(RandomValueGenerator randomValueGenerator, Factory<T> itemFactory) {
		super(randomValueGenerator, itemFactory);
	}

	/**
	 * Create a new concrete List instance that this Factory will return populated.
	 * 
	 * @return A concrete List of the type created by this Factory.
	 */
	@Override
    protected List<T> createList() {
		return new LinkedList<T>();
	}

}
