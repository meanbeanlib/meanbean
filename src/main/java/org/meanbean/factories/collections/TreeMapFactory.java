package org.meanbean.factories.collections;

import java.util.Map;
import java.util.TreeMap;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

/**
 * Factory that creates random TreeMaps of objects of the specified type.
 * 
 * @param <K>
 *            The data type of the keys the Maps created by this Factory use.
 * @param <V>
 *            The data type of the values the Maps created by this Factory contain.
 */
public class TreeMapFactory<K, V> extends MapFactoryBase<K, V> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new TreeMap object Factory.
     * 
     * @param randomValueGenerator
     *            A random value generator used by the Factory to generate random values.
     * @param keyFactory
     *            Factory used to create each Map key.
     * @param valueFactory
     *            Factory used to create each Map value.
     */
	public TreeMapFactory(RandomValueGenerator randomValueGenerator, Factory<K> keyFactory, Factory<V> valueFactory) {
		super(randomValueGenerator, keyFactory, valueFactory);
	}

	/**
	 * Create a new concrete Map instance that this Factory will return populated.
	 * 
	 * @return A concrete Map of the type created by this Factory.
	 */
	@Override
	protected Map<K, V> createMap() {
		return new TreeMap<K, V>();
	}

}
