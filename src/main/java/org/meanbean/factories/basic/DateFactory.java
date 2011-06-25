package org.meanbean.factories.basic;

import java.util.Date;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random Date objects.
 * 
 * @author Graham Williamson
 */
public final class DateFactory extends RandomFactoryBase<Date> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Date object factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public DateFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	/**
	 * Create a new Date object.
	 * 
	 * @return A new Date object.
	 */
	@Override
	public Date create() {
		// Get random time since the epoch
		long randomTime = Math.abs(getRandomValueGenerator().nextLong());
		return new Date(randomTime);
	}
}