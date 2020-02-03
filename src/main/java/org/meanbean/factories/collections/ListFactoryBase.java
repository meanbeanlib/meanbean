package org.meanbean.factories.collections;

import org.meanbean.factories.basic.RandomFactoryBase;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

import java.util.List;

/**
 * Abstract Factory that creates Lists of objects of the specified type.
 * 
 * @author Graham Williamson
 * 
 * @param <T>
 *            The data type of the object this Factory creates Lists of.
 */
public abstract class ListFactoryBase<T> extends RandomFactoryBase<List<T>> {

	/** Factory used to create each List item. */
	private final Factory<T> itemFactory;

	/**
	 * Construct a new List object Factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * @param itemFactory
	 *            Factory used to create each List item.
	 */
	public ListFactoryBase(RandomValueGenerator randomValueGenerator, Factory<T> itemFactory) {
		super(randomValueGenerator);
		ValidationHelper.ensureExists("itemFactory", "construct ListFactory", itemFactory);
		this.itemFactory = itemFactory;
	}

	/**
	 * Create a new List of objects of the specified type.
	 * 
	 * @return A new List of object of the specified type.
	 */
	@Override
    public List<T> create() {
		// Basis to randomly decide size of List
		int size = getRandomValueGenerator().nextInt(16) + 1;
		List<T> result = createList();
		for (int idx = 0; idx < size; idx++) {
			result.add(itemFactory.create());
		}
		return result;
	}

	/**
	 * Create a new concrete List instance that this Factory will return populated.
	 * 
	 * @return A concrete List of the type created by this Factory.
	 */
	protected abstract List<T> createList();

}
