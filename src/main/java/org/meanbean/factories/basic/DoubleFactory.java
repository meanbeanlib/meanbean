package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Double objects.
 * 
 * @author Graham Williamson
 */
public final class DoubleFactory extends FactoryBase<Double> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Double object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public DoubleFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Double object.
     * 
     * @return A new Double object.
     */
    @Override
    public Double create() {
    	// Basis of our random number. This value is always positive, so we need to decide the sign
    	double result = getRandomNumberGenerator().nextDouble();
        // Our double is either based on MAX_VALUE, else MIN_VALUE
        boolean basedOnMax = getRandomNumberGenerator().nextBoolean();
        result *= basedOnMax ? Double.MAX_VALUE : Double.MIN_VALUE;
        // Our double is either positive, else negative 
        boolean positive = getRandomNumberGenerator().nextBoolean();
        result *= positive ? 1 : -1;
        return result;
    }
}