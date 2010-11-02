package org.meanbean.factories.basic;

import java.util.Date;

import org.meanbean.util.RandomNumberGenerator;


/**
 * Concrete Factory that creates Date objects.
 * 
 * @author Graham Williamson
 */
public final class DateFactory extends FactoryBase<Date> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Date object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public DateFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Date object.
     * 
     * @return A new Date object.
     */
    @Override
    public Date create() {
        // Get random time since the epoch
        long randomTime = Math.abs(getRandomNumberGenerator().nextLong());
        return new Date(randomTime);
    }
}