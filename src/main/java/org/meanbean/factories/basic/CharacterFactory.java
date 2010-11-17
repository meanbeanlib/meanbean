package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete Factory that creates random Character objects.
 * 
 * @author Graham Williamson
 */
public final class CharacterFactory extends RandomFactoryBase<Character> {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Construct a new Character object factory.
     * 
     * @param randomValueGenerator
     *            A random value generator used by the Factory to generate random values.
     *            
     * @throws IllegalArgumentException
     *             If the specified randomValueGenerator is deemed illegal. For example, if it is null.
     */
    public CharacterFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
        super(randomValueGenerator);
    }

    /**
     * Create a new Character object.
     * 
     * @return A new Character object.
     */
    @Override
    public Character create() {
        // Basis of our random number. This value is always positive.
        double randomNumber = getRandomValueGenerator().nextDouble();
        char result = (char) (Character.MAX_VALUE * randomNumber);
        return result;
    }
}