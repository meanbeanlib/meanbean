package org.meanbean.factories.basic;

import org.meanbean.util.RandomNumberGenerator;

/**
 * Concrete Factory that creates Character objects.
 * 
 * @author Graham Williamson
 */
public final class CharacterFactory extends FactoryBase<Character> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Character object factory.
     * 
     * @param randomNumberGenerator
     *            A random number generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomNumberGenerator is deemed illegal. For example, if it is null.
     */
    public CharacterFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
        super(randomNumberGenerator);
    }

    /**
     * Create a new Character object.
     * 
     * @return A new Character object.
     */
    @Override
    public Character create() {
        // Basis of our random number. This value is always positive.
        double randomNumber = getRandomNumberGenerator().nextDouble();
        char result = (char) (Character.MAX_VALUE * randomNumber);
        return result;
    }
}