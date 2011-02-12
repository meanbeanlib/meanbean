package org.meanbean.util;

/**
 * Defines an object that affords helpful input validation functionality.
 * 
 * @author Graham Williamson
 */
public interface ValidationHelper {

	/**
	 * <p>
	 * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br/>
	 * </p>
	 * 
	 * <p>
	 * The exception thrown will contain the name of the parameter that must exist.
	 * </p>
	 * 
	 * @param name
	 *            The name of the object that must exist.
	 * @param value
	 *            The object that must exist.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified value does not exist.
	 */
	void ensureExists(String name, Object value) throws IllegalArgumentException;

	/**
	 * <p>
	 * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br/>
	 * </p>
	 * 
	 * <p>
	 * The exception thrown will contain the name of the parameter that must exist as well as the operation being
	 * attempted.
	 * </p>
	 * 
	 * @param name
	 *            The name of the object that must exist.
	 * @param operation
	 *            The operation being attempted.
	 * @param value
	 *            The object that must exist.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified value does not exist.
	 */
	void ensureExists(String name, String operation, Object value) throws IllegalArgumentException;
}