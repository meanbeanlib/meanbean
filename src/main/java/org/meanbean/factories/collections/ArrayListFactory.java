package org.meanbean.factories.collections;

import java.util.ArrayList;
import java.util.List;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

/**
 * Factory that creates ArrayLists of objects of the specified type.
 * 
 * @author Graham Williamson
 * 
 * @param <T>
 *            The data type of the object this Factory creates ArrayLists of.
 */
public class ArrayListFactory<T> extends ListFactoryBase<T> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new ArrayList object Factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     * @param itemFactory
     *            Factory used to create each ArrayList item.
     */
    public ArrayListFactory(RandomNumberGenerator randomNumberGenerator,Factory<T> itemFactory) {
        super(randomNumberGenerator,itemFactory);
    }

	/**
	 * Create a new concrete List instance that this Factory will return populated.
	 * 
	 * @return A concrete List of the type created by this Factory.
	 */
	protected List<T> createList() {
		return new ArrayList<T>();
	}
    
}
