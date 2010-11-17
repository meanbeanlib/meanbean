package org.meanbean.bean.factory;

/**
 * An exception that may be thrown when trying to create an instance of a JavaBean.
 * 
 * @author Graham Williamson
 */
public class BeanCreationException extends RuntimeException {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Bean Creation Exception with the specified message and cause.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 * @param cause
	 *            The cause of the exception.
	 */
	public BeanCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construct a new Bean Creation Exception with the specified message.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 */
	public BeanCreationException(String message) {
		super(message);
	}
}