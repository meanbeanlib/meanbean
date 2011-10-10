package org.meanbean.factories.basic;

import java.math.BigDecimal;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random BigDecimal objects.
 * 
 * @author Graham Williamson
 */
public final class BigDecimalFactory extends RandomFactoryBase<BigDecimal> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new BigDecimal object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public BigDecimalFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new Double object.
	 * 
	 * @return A new Double object.
	 */
	@Override
	public BigDecimal create() {
		return new BigDecimal(getRandomValueGenerator().nextLong());
	}
}