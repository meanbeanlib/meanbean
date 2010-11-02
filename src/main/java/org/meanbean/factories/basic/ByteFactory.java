package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Byte objects.
 * 
 * @author Graham Williamson
 */
public final class ByteFactory extends FactoryBase<Byte> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Byte object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public ByteFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Byte object.
     * 
     * @return A new Byte object.
     */
    @Override
    public Byte create() {
        return getRandomNumberGenerator().nextByte();
    }
}