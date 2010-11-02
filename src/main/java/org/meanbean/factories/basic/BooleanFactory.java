package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Boolean objects.
 * 
 * @author Graham Williamson
 */
public final class BooleanFactory extends FactoryBase<Boolean> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Boolean object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public BooleanFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Boolean object.
     * 
     * @return A new Boolean object.
     */
    @Override
    public Boolean create() {
        return getRandomNumberGenerator().nextBoolean();
    }
}