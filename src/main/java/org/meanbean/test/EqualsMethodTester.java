package org.meanbean.test;

import org.meanbean.bean.info.BeanInformationException;
import org.meanbean.lang.Factory;

/**
 * Defines a means of testing the correctness of the equals logic implemented by a type with respect to:
 * 
 * <ul>
 * <li>the general equals contract</li>
 * 
 * <li>the programmer's expectation of property significance in object equality</li>
 * </ul>
 * 
 * The following is tested:
 * 
 * <ul>
 * <li>the <strong>reflexive</strong> item of the equals contract - <code>x.equals(x)</code> should hold</li>
 * 
 * <li>the <strong>symmetric</strong> item of the equals contract - if <code>x.equals(y)</code>, then
 * <code>y.equals(x)</code> should also hold</li>
 * 
 * <li>the <strong>transitive</strong> item of the equals contract - if <code>x.equals(y)</code> and
 * <code>y.equals(z)</code>, then <code>x.equals(z)</code> should hold</li>
 * 
 * <li>the <strong>consistent</strong> item of the equals contract - if <code>x.equals(y)</code>, then
 * <code>x.equals(y)</code> should hold (remain consistent) across multiple invocations, so long as neither object
 * changes</li>
 * 
 * <li>the <strong>null</strong> item of the equals contract - a non-null object should not be deemed equal to another
 * <code>null</code> object</li>
 * 
 * <li>that an entirely different type of object is not deemed equal to an object created by the specified factory.</li>
 * 
 * <li>that the equality of an object <strong>is not</strong> affected by properties that <strong>are not</strong>
 * considered in the equality logic</li>
 * 
 * <li>that the equality of an object <strong>is</strong> affected by properties that <strong>are</strong> considered in
 * the equality logic</li>
 * </ul>
 * 
 * @author Graham Williamson
 */
public interface EqualsMethodTester {

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is correct by testing:
	 * 
	 * <ul>
	 * <li>the <strong>reflexive</strong> item of the equals contract - <code>x.equals(x)</code> should hold</li>
	 * 
	 * <li>the <strong>symmetric</strong> item of the equals contract - if <code>x.equals(y)</code>, then
	 * <code>y.equals(x)</code> should also hold</li>
	 * 
	 * <li>the <strong>transitive</strong> item of the equals contract - if <code>x.equals(y)</code> and
	 * <code>y.equals(z)</code>, then <code>x.equals(z)</code> should hold</li>
	 * 
	 * <li>the <strong>consistent</strong> item of the equals contract - if <code>x.equals(y)</code>, then
	 * <code>x.equals(y)</code> should hold (remain consistent) across multiple invocations, so long as neither object
	 * changes</li>
	 * 
	 * <li>the <strong>null</strong> item of the equals contract - a non-null object should not be deemed equal to
	 * another <code>null</code> object</li>
	 * 
	 * <li>that an entirely different type of object is not deemed equal to an object created by the specified factory.</li>
	 * 
	 * <li>that the equality of an object <strong>is not</strong> affected by properties that <strong>are not</strong>
	 * considered in the equality logic</li>
	 * 
	 * <li>that the equality of an object <strong>is</strong> affected by properties that <strong>are</strong>
	 * considered in the equality logic</li>
	 * </ul>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is correct. The factory must create logically equivalent but
	 *            different actual instances of the type upon each invocation of <code>create()</code> in order for the
	 *            test to be meaningful and correct.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	void testEqualsMethod(Factory<?> factory, String... insignificantProperties) throws IllegalArgumentException,
	        BeanInformationException, BeanTestException, AssertionError;

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is correct by testing:
	 * 
	 * <ul>
	 * <li>the <strong>reflexive</strong> item of the equals contract - <code>x.equals(x)</code> should hold</li>
	 * 
	 * <li>the <strong>symmetric</strong> item of the equals contract - if <code>x.equals(y)</code>, then
	 * <code>y.equals(x)</code> should also hold</li>
	 * 
	 * <li>the <strong>transitive</strong> item of the equals contract - if <code>x.equals(y)</code> and
	 * <code>y.equals(z)</code>, then <code>x.equals(z)</code> should hold</li>
	 * 
	 * <li>the <strong>consistent</strong> item of the equals contract - if <code>x.equals(y)</code>, then
	 * <code>x.equals(y)</code> should hold (remain consistent) across multiple invocations, so long as neither object
	 * changes</li>
	 * 
	 * <li>the <strong>null</strong> item of the equals contract - a non-null object should not be deemed equal to
	 * another <code>null</code> object</li>
	 * 
	 * <li>that an entirely different type of object is not deemed equal to an object created by the specified factory.</li>
	 * 
	 * <li>that the equality of an object <strong>is not</strong> affected by properties that <strong>are not</strong>
	 * considered in the equality logic</li>
	 * 
	 * <li>that the equality of an object <strong>is</strong> affected by properties that <strong>are</strong>
	 * considered in the equality logic</li>
	 * </ul>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is correct. The factory must create logically equivalent but
	 *            different actual instances of the type upon each invocation of <code>create()</code> in order for the
	 *            test to be meaningful and correct.
	 * @param configuration
	 *            A custom Configuration to be used when testing to ignore the testing of named properties or use a
	 *            custom test data Factory when testing a named property. This Configuration is only used for this
	 *            individual test and will not be retained for future testing of this or any other type. If no custom
	 *            Configuration is required, pass <code>null</code> or use
	 *            <code>testEqualsMethod(Factory<?>,String...)</code> instead.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	void testEqualsMethod(Factory<?> factory, Configuration configuration, String... insignificantProperties)
	        throws IllegalArgumentException, AssertionError;

}