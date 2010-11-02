package org.meanbean.factories.collections;

import java.util.HashMap;
import java.util.Map;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

/**
 * Factory that creates HashMaps of objects of the specified type.
 * 
 * @param <K>
 *            The data type of the keys the Maps created by this Factory use.
 * @param <V>
 *            The data type of the values the Maps created by this Factory contain.
 */
public class HashMapFactory<K, V> extends MapFactoryBase<K, V> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new HashMap object Factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     * @param keyFactory
     *            Factory used to create each Map key.
     * @param valueFactory
     *            Factory used to create each Map value.
     */
	public HashMapFactory(RandomNumberGenerator randomNumberGenerator, Factory<K> keyFactory, Factory<V> valueFactory) {
		super(randomNumberGenerator, keyFactory, valueFactory);
	}

	/**
	 * Create a new concrete Map instance that this Factory will return populated.
	 * 
	 * @return A concrete Map of the type created by this Factory.
	 */
	@Override
	protected Map<K, V> createMap() {
		return new HashMap<K, V>();
	}

}
