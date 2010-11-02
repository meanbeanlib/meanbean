package org.meanbean.factories.collections;

import java.util.Set;

import org.meanbean.factories.Factory;
import org.meanbean.factories.basic.FactoryBase;
import org.meanbean.util.RandomNumberGenerator;

/**
 * Abstract Factory that creates Sets of objects of the specified type.
 * 
 * @param <T>
 *            The data type of the object this Factory creates Sets of.
 */
public abstract class SetFactoryBase<T> extends FactoryBase<Set<T>> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /** Factory used to create each Set item. */
    private final Factory<T> itemFactory;
    
    /**
     * Construct a new Set object Factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     * @param itemFactory
     *            Factory used to create each Set item.
     */
    public SetFactoryBase(RandomNumberGenerator randomNumberGenerator,Factory<T> itemFactory) {
        super(randomNumberGenerator);
        validationHelper.ensureExists("itemFactory", "construct SetFactory", itemFactory);
        this.itemFactory = itemFactory;
    }

    /**
     * Create a new Set of objects of the specified type.
     * 
     * @return A new Set of object of the specified type.
     */
    public Set<T> create() {
        // Basis to randomly decide size of Set
        double randomSize = getRandomNumberGenerator().nextDouble();
        int size = (int)(100.0 * randomSize);
        Set<T> result = createSet();
        for (int idx = 0; idx < size; idx++) {
            result.add(itemFactory.create());
        }
        return result;
    }

	/**
	 * Create a new concrete Set instance that this Factory will return populated.
	 * 
	 * @return A concrete Set of the type created by this Factory.
	 */
	protected abstract Set<T> createSet();
    
}