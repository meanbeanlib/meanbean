package org.meanbean.factories.collections;

import java.util.LinkedHashSet;
import java.util.Set;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

/**
 * Factory that creates random LinkedHashSets of objects of the specified type.
 * 
 * @param <T>
 *            The data type of the object this Factory creates Sets of.
 */
public class LinkedHashSetFactory<T> extends SetFactoryBase<T> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
        
    /**
     * Construct a new LinkedHashSet object Factory.
     * 
     * @param randomValueGenerator
     *            A random value generator used by the Factory to generate random values.
     * @param itemFactory
     *            Factory used to create each Set item.
     */
    public LinkedHashSetFactory(RandomValueGenerator randomValueGenerator,Factory<T> itemFactory) {
        super(randomValueGenerator,itemFactory);
    }

	/**
	 * Create a new concrete Set instance that this Factory will return populated.
	 * 
	 * @return A concrete Set of the type created by this Factory.
	 */
    @Override
	protected Set<T> createSet() {
		return new LinkedHashSet<T>();
	}
    
}
