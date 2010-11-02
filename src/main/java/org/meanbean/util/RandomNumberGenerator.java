package org.meanbean.util;

/**
 * Defines an object that generates random numbers between 0.0 and 1.0.
 * 
 * @author Graham Williamson
 */
public interface RandomNumberGenerator {

    /**
     * Generate a random byte.
     * 
     * @return A randomly generated byte, which may be positive or negative.
     */
    byte nextByte();

    /**
     * Generate a random array of bytes.
     * 
     * @param size
     *            The number of bytes to generate and return. This cannot be a negative number.
     * 
     * @return An array of <i>size</i> randomly generated bytes, each of which may be positive or negative.
     * 
     * @throws IllegalArgumentException
     *             If the size parameter is deemed illegal. For example, if it is a negative number.
     */
    byte[] nextBytes(int size);

    /**
     * Generate a random int.
     * 
     * @return A randomly generated int, which may be positive or negative.
     */
    int nextInt();

    /**
     * Generate a random double between 0.0 (inclusive) and 1.0 (exclusive).
     * 
     * @return A randomly generated double.
     */
    long nextLong();

    /**
     * Generate a random float between 0.0f (inclusive) and 1.0f (exclusive).
     * 
     * @return A randomly generated float.
     */
    float nextFloat();

    /**
     * Generate a random double between 0.0d (inclusive) and 1.0d (exclusive).
     * 
     * @return A randomly generated double.
     */
    double nextDouble();

    /**
     * Generate a random boolean.
     * 
     * @return A randomly generated boolean.
     */
    boolean nextBoolean();

}