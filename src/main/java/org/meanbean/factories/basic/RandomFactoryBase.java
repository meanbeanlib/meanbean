package org.meanbean.factories.basic;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Abstract base class for a Factory that creates random objects of the specified type.
 * 
 * @author Graham Williamson
 * @param <T>
 *            The data type of the object this Factory creates.
 */
public abstract class RandomFactoryBase<T> implements Factory<T>, RandomValueGeneratorProvider {

	/** Input validation helper. */
	protected final ValidationHelper validationHelper = new SimpleValidationHelper();

	/** Random number generator used by the factory to generate random values. */
	private final RandomValueGenerator randomValueGenerator;

	/**
	 * Construct a new Factory.
	 * 
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
	 */
	public RandomFactoryBase(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		validationHelper.ensureExists("randomValueGenerator", "construct Factory", randomValueGenerator);
		this.randomValueGenerator = randomValueGenerator;
	}

	/**
	 * Get the random value generator.
	 * 
	 * @return A random value generator.
	 */
	@Override
    public final RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	/**
	 * Create a new object of the specified type.
	 * 
	 * @return A new object of the specified type.
	 */
	@Override
    public abstract T create();
}