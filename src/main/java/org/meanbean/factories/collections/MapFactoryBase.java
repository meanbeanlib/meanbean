package org.meanbean.factories.collections;

import org.meanbean.factories.basic.RandomFactoryBase;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

import java.util.Map;

/**
 * Abstract Factory that creates Maps of objects of the specified type.
 * 
 * @param <K>
 *            The data type of the keys the Maps created by this Factory use.
 * @param <V>
 *            The data type of the values the Maps created by this Factory contain.
 */
public abstract class MapFactoryBase<K, V> extends RandomFactoryBase<Map<K, V>> {

	/** Factory used to create each Map key. */
	private final Factory<K> keyFactory;

	/** Factory used to create each Map value. */
	private final Factory<V> valueFactory;

	/**
	 * Construct a new Map object Factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * @param keyFactory
	 *            Factory used to create each Map key.
	 * @param valueFactory
	 *            Factory used to create each Map value.
	 */
	public MapFactoryBase(RandomValueGenerator randomValueGenerator, Factory<K> keyFactory, Factory<V> valueFactory) {
		super(randomValueGenerator);
		ValidationHelper.ensureExists("keyFactory", "construct MapFactory", keyFactory);
		ValidationHelper.ensureExists("valueFactory", "construct MapFactory", valueFactory);
		this.keyFactory = keyFactory;
		this.valueFactory = valueFactory;
	}

	/**
	 * Create a new Map of objects of the specified type.
	 * 
	 * @return A new Map of object of the specified type.
	 */
	@Override
    public Map<K, V> create() {
		// Basis to randomly decide size of Map
		double randomSize = getRandomValueGenerator().nextDouble();
		int size = (int) (100.0 * randomSize);
		Map<K, V> result = createMap();
		for (int idx = 0; idx < size; idx++) {
			result.put(keyFactory.create(), valueFactory.create());
		}
		return result;
	}

	/**
	 * Create a new concrete Map instance that this Factory will return populated.
	 * 
	 * @return A concrete Map of the type created by this Factory.
	 */
	protected abstract Map<K, V> createMap();

}
