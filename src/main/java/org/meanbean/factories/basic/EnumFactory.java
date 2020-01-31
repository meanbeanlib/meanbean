package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete Factory that creates random Enum constants of the specified Enum type.
 * 
 * @author Graham Williamson
 */
public class EnumFactory extends RandomFactoryBase<Enum<?>> {

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper();

	/** Enum constants of the specified Enum type. */
	private final Enum<?>[] enumConstants;

	/**
	 * Construct a new Factory.
	 * 
	 * @param enumClass
	 *            The type of Enum to create Enum constants of.
	 * @param randomValueGenerator
	 *            A random value generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the required parameters are deemed illegal. For example, if either is null.
	 */
	public EnumFactory(Class<?> enumClass, RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
		validationHelper.ensureExists("enumClass", "construct EnumFactory", enumClass);
		if (!enumClass.isEnum()) {
			throw new IllegalArgumentException("Cannot create EnumFactory for non-Enum class.");
		}
		this.enumConstants = (Enum<?>[]) enumClass.getEnumConstants();
	}

	/**
	 * Create an Enum constant of the specified Enum type.
	 * 
	 * @return An Enum constant of the specified Enum type.
	 */
	@Override
	public Enum<?> create() {
		// Basis to randomly select enum constant from
		double random = getRandomValueGenerator().nextDouble();
		// Get ordinal from random number
		int ordinal = ((int) ((enumConstants.length - 1) * random));
		// Get enum constant from ordinal
		return enumConstants[ordinal];
	}
}