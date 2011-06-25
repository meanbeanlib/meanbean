package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random Double objects.
 * 
 * @author Graham Williamson
 */
public final class DoubleFactory extends RandomFactoryBase<Double> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Double object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public DoubleFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new Double object.
	 * 
	 * @return A new Double object.
	 */
	@Override
	public Double create() {
		// Basis of our random number. This value is always positive, so we need to decide the sign
		double result = getRandomValueGenerator().nextDouble();
		// Our double is either based on MAX_VALUE, else MIN_VALUE
		boolean basedOnMax = getRandomValueGenerator().nextBoolean();
		result *= basedOnMax ? Double.MAX_VALUE : Double.MIN_VALUE;
		// Our double is either positive, else negative
		boolean positive = getRandomValueGenerator().nextBoolean();
		result *= positive ? 1 : -1;
		return result;
	}
}