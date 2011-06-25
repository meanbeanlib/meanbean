package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random Float objects.
 * 
 * @author Graham Williamson
 */
public final class FloatFactory extends RandomFactoryBase<Float> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Float object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public FloatFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new Float object.
	 * 
	 * @return A new Float object.
	 */
	@Override
	public Float create() {
		// Basis of our random number. This value is always positive, so we need to decide the sign
		float result = getRandomValueGenerator().nextFloat();
		// Our float is either based on MAX_VALUE, else MIN_VALUE
		boolean basedOnMax = getRandomValueGenerator().nextBoolean();
		result *= basedOnMax ? Float.MAX_VALUE : Float.MIN_VALUE;
		// Our float is either positive, else negative
		boolean positive = getRandomValueGenerator().nextBoolean();
		result *= positive ? 1 : -1;
		return result;
	}
}