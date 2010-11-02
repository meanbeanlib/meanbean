package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete Factory that creates Enum constants of the specified Enum type.
 * 
 * @author Graham Williamson
 */
public class EnumFactory extends FactoryBase<Enum<?>> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper();

	/** Enum constants of the specified Enum type. */
	private final Enum<?>[] enumConstants;

	/**
	 * Construct a new Factory.
	 * 
	 * @param enumClass
	 *            The type of Enum to create Enum constants of.
	 * @param randomNumberGenerator
	 *            A random number generator used by the Factory to generate random values.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the required parameters are deemed illegal. For example, if either is null.
	 */
	public EnumFactory(Class<?> enumClass, RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
		super(randomNumberGenerator);
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
		double random = getRandomNumberGenerator().nextDouble();
		// Get ordinal from random number
		int ordinal = ((int) ((enumConstants.length - 1) * random));
		// Get enum constant from ordinal
		return enumConstants[ordinal];
	}
}