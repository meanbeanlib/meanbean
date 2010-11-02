package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Float objects.
 * 
 * @author Graham Williamson
 */
public final class FloatFactory extends FactoryBase<Float> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Float object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public FloatFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Float object.
     * 
     * @return A new Float object.
     */
    @Override
    public Float create() {
    	// Basis of our random number. This value is always positive, so we need to decide the sign
    	float result = getRandomNumberGenerator().nextFloat();
        // Our float is either based on MAX_VALUE, else MIN_VALUE
        boolean basedOnMax = getRandomNumberGenerator().nextBoolean();
        result *= basedOnMax ? Float.MAX_VALUE : Float.MIN_VALUE;
        // Our float is either positive, else negative 
        boolean positive = getRandomNumberGenerator().nextBoolean();
        result *= positive ? 1 : -1;
        return result;
    }
}