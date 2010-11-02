package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Integer objects.
 * 
 * @author Graham Williamson
 */
public final class IntegerFactory extends FactoryBase<Integer> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Integer object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public IntegerFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Integer object.
     * 
     * @return A new Integer object.
     */
    @Override
    public Integer create() {
        return getRandomNumberGenerator().nextInt();
    }
}