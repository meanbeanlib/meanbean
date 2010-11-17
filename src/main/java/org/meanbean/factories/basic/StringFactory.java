package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random String objects.
 * 
 * @author Graham Williamson
 */
public final class StringFactory extends RandomFactoryBase<String> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new String object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public StringFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new String object.
	 * 
	 * @return A new String object.
	 */
	@Override
	public String create() {
		return "TestString:[" + getRandomValueGenerator().nextLong() + "]";
	}
}