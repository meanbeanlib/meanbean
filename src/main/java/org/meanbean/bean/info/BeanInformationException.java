package org.meanbean.bean.info;

/**
 * An exception that may be thrown when trying to gather JavaBean-related information on a type. This may be because the
 * type is not a valid JavaBean or because an unexpected error occurred when gathering the information.
 * 
 * @author Graham Williamson
 */
public class BeanInformationException extends RuntimeException {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct a new Bean Information Exception with the specified message and cause.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 * @param cause
	 *            The cause of the exception.
	 */
	public BeanInformationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construct a new Bean Information Exception with the specified message.
	 * 
	 * @param message
	 *            A human-readable String message describing the problem that occurred.
	 */
	public BeanInformationException(String message) {
		super(message);
	}
}