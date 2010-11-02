package org.meanbean.factories.collections;

import java.util.LinkedList;
import java.util.List;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

/**
 * Factory that creates LinkedLists of objects of the specified type.
 * 
 * @author Graham Williamson
 * 
 * @param <T>
 *            The data type of the object this Factory creates LinkedLists of.
 */
public class LinkedListFactory<T> extends ListFactoryBase<T> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new LinkedList object Factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     * @param itemFactory
     *            Factory used to create each LinkedList item.
     */
    public LinkedListFactory(RandomNumberGenerator randomNumberGenerator,Factory<T> itemFactory) {
        super(randomNumberGenerator,itemFactory);
    }

	/**
	 * Create a new concrete List instance that this Factory will return populated.
	 * 
	 * @return A concrete List of the type created by this Factory.
	 */
	protected List<T> createList() {
		return new LinkedList<T>();
	}
    
}
