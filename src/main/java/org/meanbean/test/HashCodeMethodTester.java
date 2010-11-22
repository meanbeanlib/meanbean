package org.meanbean.test;

import org.meanbean.lang.Factory;

/**
 * Defines a means of testing the correctness of the hashCode logic implemented by a type with respect to:
 * 
 * <ul>
 * <li>the general hashCode contract</li>
 * </ul>
 * 
 * The following is tested:
 * 
 * <ul>
 * <li>that logically equivalent objects have the same hashCode</li>
 * 
 * <li>the <strong>consistent</strong> item of the hashCode contract - the hashCode of an object should remain
 * consistent across multiple invocations, so long as the object does not change</li>
 * </ul>
 * 
 * @author Graham Williamson
 */

public interface HashCodeMethodTester {

	/**
	 * Test that the hashCode logic implemented by the type the specified factory creates is correct by testing:
	 * 
	 * <ul>
	 * <li>that logically equivalent objects have the same hashCode</li>
	 * 
	 * <li>the <strong>consistent</strong> item of the hashCode contract - the hashCode of an object should remain
	 * consistent across multiple invocations, so long as the object does not change</li>
	 * </ul>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is correct. The factory must create logically equivalent but
	 *            different actual instances of the type upon each invocation of <code>create()</code> in order for the
	 *            test to be meaningful and correct.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code>, if it creates a
	 *             <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	void testHashCodeMethod(Factory<?> factory) throws IllegalArgumentException, AssertionError;
}