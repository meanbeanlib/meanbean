package org.meanbean.test;

/**
 * An exception that may be thrown when trying to test a JavaBean using BeanTester.
 * 
 * @author Graham Williamson
 */
public class BeanTestException extends RuntimeException {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new Bean Test Exception with the specified message.
     * 
     * @param message
     *            A human-readable String message describing the problem that occurred.
     */
    public BeanTestException(String message) {
        super(message);
    }

    /**
     * Construct a new Bean Test Exception with the specified message and cause.
     * 
     * @param message
     *            A human-readable String message describing the problem that occurred.
     * @param cause
     *            The cause of the exception.
     */
    public BeanTestException(String message, Throwable cause) {
        super(message, cause);
    }
}