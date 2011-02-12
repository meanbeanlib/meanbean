package org.meanbean.test;

/**
 * Defines a means of verifying whether the equality of an object and its property is consistent.
 * 
 * @author Graham Williamson
 */
interface ObjectPropertyEqualityConsistentAsserter {

	/**
	 * <p>
	 * Assert that the equality of two logically equivalent objects is consistent when a change is made to a property of
	 * one of the objects. <br/>
	 * </p>
	 * 
	 * <p>
	 * It is permissible to check the equality of two objects when no change has been made to the named property. This
	 * check is performed when logically equivalent objects are passed to the <i>originalPropertyValue</i> and
	 * <i>newPropertyValue</i> arguments.
	 * </p>
	 * 
	 * @param propertyName
	 *            The name of the property that may have changed.
	 * @param originalObject
	 *            The object that the modified object was logically equivalent to prior to its modification.
	 * @param modifiedObject
	 *            The modified object, which was logically equivalent to the original object prior to the modification
	 *            of its named property.
	 * @param originalPropertyValue
	 *            The original value of the modified property. This should still be the value of the named property in
	 *            the original object.
	 * @param newPropertyValue
	 *            The new value of the named property in the modified object.
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the parameters are deemed illegal. For example, if any are <code>null</code>.
	 * @throws AssertionError
	 *             If the equality of the modified object is inconsistent with the changes made to its property.
	 */
	void assertConsistent(String propertyName, Object originalObject, Object modifiedObject,
	        Object originalPropertyValue, Object newPropertyValue) throws IllegalArgumentException, AssertionError;

}