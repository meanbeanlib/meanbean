package org.meanbean.factories;

/**
 * An exception that may be thrown when trying to create an instance of an object.
 * 
 * @author Graham Williamson
 */
public class ObjectCreationException extends RuntimeException {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Object Creation Exception with the specified message and cause.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 * @param cause
	 *            The cause of the exception.
	 */
	public ObjectCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construct a new Object Creation Exception with the specified message.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 */
	public ObjectCreationException(String message) {
		super(message);
	}
}