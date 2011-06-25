package org.meanbean.test;

/**
 * <p>
 * Defines a means of testing the correctness of the hashCode logic implemented by a type, based solely on the provision
 * of the type, with respect to:
 * </p>
 * 
 * <ul>
 * <li>the general hashCode contract</li>
 * </ul>
 * 
 * <p>
 * The following is tested:
 * </p>
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
public interface SmartHashCodeMethodTester {

	/**
	 * <p>
	 * Test that the hashCode logic implemented by the specified type is correct by testing:
	 * </p>
	 * 
	 * <ul>
	 * <li>that logically equivalent objects have the same hashCode</li>
	 * 
	 * <li>the <strong>consistent</strong> item of the hashCode contract - the hashCode of an object should remain
	 * consistent across multiple invocations, so long as the object does not change</li>
	 * </ul>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param clazz
	 *            The type to test the equals logic of.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified clazz is deemed illegal. For example, if it is <code>null</code>.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	void testHashCodeMethod(Class<?> clazz) throws IllegalArgumentException, AssertionError;
}