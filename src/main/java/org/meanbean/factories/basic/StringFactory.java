package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates String objects.
 * 
 * @author Graham Williamson
 */
public final class StringFactory extends FactoryBase<String> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new String object factory.
	 * 
	 * @param randomNumberGenerator
	 *            A random number generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
	 */
	public StringFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
		super(randomNumberGenerator);
	}

	/**
	 * Create a new String object.
	 * 
	 * @return A new String object.
	 */
	@Override
	public String create() {
		return "TestString:[" + getRandomNumberGenerator().nextLong() + "]";
	}
}