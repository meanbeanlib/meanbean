package org.meanbean.factories.collections;

import java.util.List;

import org.meanbean.factories.Factory;
import org.meanbean.factories.basic.FactoryBase;
import org.meanbean.util.RandomNumberGenerator;

/**
 * Abstract Factory that creates Lists of objects of the specified type.
 * 
 * @author Graham Williamson
 * 
 * @param <T>
 *            The data type of the object this Factory creates Lists of.
 */
public abstract class ListFactoryBase<T> extends FactoryBase<List<T>> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /** Factory used to create each List item. */
    private final Factory<T> itemFactory;
    
    /**
     * Construct a new List object Factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     * @param itemFactory
     *            Factory used to create each List item.
     */
    public ListFactoryBase(RandomNumberGenerator randomNumberGenerator,Factory<T> itemFactory) {
        super(randomNumberGenerator);
        validationHelper.ensureExists("itemFactory", "construct ListFactory", itemFactory);
        this.itemFactory = itemFactory;
    }

    /**
     * Create a new List of objects of the specified type.
     * 
     * @return A new List of object of the specified type.
     */
    public List<T> create() {
        // Basis to randomly decide size of List
        double randomSize = getRandomNumberGenerator().nextDouble();
        int size = (int)(100.0 * randomSize);
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
