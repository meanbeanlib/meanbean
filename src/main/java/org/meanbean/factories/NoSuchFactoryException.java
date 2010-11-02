package org.meanbean.factories;

/**
 * An exception that may be thrown when trying to get an unknown or unregistered Factory from the FactoryRepository.
 * 
 * @author Graham Williamson
 */
public class NoSuchFactoryException extends RuntimeException {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new No Such Factory Exception with the specified message.
     * 
     * @param message
     *            A human-readable String message describing the problem that occurred.
     */
    public NoSuchFactoryException(String message) {
        super(message);
    }

    /**
     * Construct a new No Such Factory Exception with the specified message and cause.
     * 
     * @param message
     *            A human-readable String message describing the problem that occurred.
     * @param cause
     *            The cause of the exception.
     */
    public NoSuchFactoryException(String message, Throwable cause) {
        super(message, cause);
    }    
}