package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Short objects.
 * 
 * @author Graham Williamson
 */
public final class ShortFactory extends FactoryBase<Short> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Short object factory.
	 * 
	 * @param randomNumberGenerator
	 *            A random number generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
	 */
	public ShortFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
		super(randomNumberGenerator);
	}

	/**
	 * Create a new Short object.
	 * 
	 * @return A new Short object.
	 */
	@Override
	public Short create() {
		short result = 0;
		// Basis of our random number. This value is always positive, so we need to decide the sign
		double randomNumber = getRandomNumberGenerator().nextDouble();
		// Our short is either based on MAX_VALUE, else MIN_VALUE
		boolean basedOnMax = getRandomNumberGenerator().nextBoolean();
		if (basedOnMax) {
			result = (short) (Short.MAX_VALUE * randomNumber);
		} else {
			result = (short) (Short.MIN_VALUE * randomNumber);
		}
		// Our double is either positive, else negative
		boolean positive = getRandomNumberGenerator().nextBoolean();
		result *= positive ? 1 : -1;
		return result;
	}
}