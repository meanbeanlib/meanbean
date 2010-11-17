package org.meanbean.util;

/**
 * Utility methods for assertions.
 * 
 * @author Graham Williamson
 */
public class AssertionUtils {

	/**
	 * Construct a new AssertionUtils.
	 */
	private AssertionUtils() {
		// Do nothing - make non-instantiable
	}

	/**
	 * Fail an assertion, which will throw an <code>AssertionError</code> with no message.
	 */
	public static void fail() {
		throw new AssertionError();
	}

	/**
	 * Fail an assertion, which will throw an <code>AssertionError</code> with the specified message.
	 * 
	 * @param message
	 *            A message detailing the assertion failure.
	 */
	public static void fail(String message) {
		throw new AssertionError(message);
	}
}