package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random Integer objects.
 * 
 * @author Graham Williamson
 */
public final class IntegerFactory extends RandomFactoryBase<Integer> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Integer object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public IntegerFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new Integer object.
	 * 
	 * @return A new Integer object.
	 */
	@Override
	public Integer create() {
		return getRandomValueGenerator().nextInt();
	}
}