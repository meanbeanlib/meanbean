package org.meanbean.factories.basic;

import java.io.Serializable;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.RandomNumberGeneratorProvider;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Abstract base class for a Factory that creates objects of the specified type.
 * 
 * @author Graham Williamson
 * @param <T>
 *            The data type of the object this Factory creates.
 */
public abstract class FactoryBase<T> implements Factory<T>, RandomNumberGeneratorProvider, Serializable {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /** Input validation helper. */
    protected final ValidationHelper validationHelper = new SimpleValidationHelper();
    
    /** Random number generator used by the factory to generate random values. */
    private final RandomNumberGenerator randomNumberGenerator;

    /**
     * Construct a new Factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     * 
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public FactoryBase(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        validationHelper.ensureExists("randomNumberGenerator", "construct Factory", randomNumberGenerator);
        this.randomNumberGenerator = randomNumberGenerator;
    }

    /**
     * Get the random number generator.
     * 
     * @return A random number generator.
     */
    public final RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    /**
     * Create a new object of the specified type.
     * 
     * @return A new object of the specified type.
     */
    public abstract T create();
}