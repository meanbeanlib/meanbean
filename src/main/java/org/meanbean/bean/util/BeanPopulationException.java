package org.meanbean.bean.util;

/**
 * An exception that may be thrown when trying to populate the fields of a JavaBean.
 * 
 * @author Graham Williamson
 */
public class BeanPopulationException extends RuntimeException {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Bean Population Exception with the specified message and cause.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 * @param cause
	 *            The cause of the exception.
	 */
	public BeanPopulationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construct a new Bean Population Exception with the specified message.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 */
	public BeanPopulationException(String message) {
		super(message);
	}
}