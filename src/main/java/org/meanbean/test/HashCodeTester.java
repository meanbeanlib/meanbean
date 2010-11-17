package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Class that affords functionality to test the hashCode logic implemented by objects. <br/>
 * 
 * Use the tests provided by this class (namely, <code>testHashCode()</code>) to test a class that overrides
 * <code>equals()</code> and <code>hashCode()</code>. <code>testHashCode()</code> invokes all other tests. However, you
 * can invoke each test individually instead. <br/>
 * 
 * As an example, to test the hashCode logic implemented by a class called MyClass do the following:
 * 
 * <pre>
 * HashCodeTester hashCodeTester = new HashCodeTester();
 * hashCodeTester.testHashCode(new Factory<MyClass>() {
 *    @Override
 *    public MyClass create() {
 *       MyClass() result = new MyClass();
 *       // initialize result...
 *       result.setName("TEST_NAME");
 *       return result;
 *    }
 * });
 * </pre>
 * 
 * The Factory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>.
 * 
 * @author Graham Williamson
 */
public class HashCodeTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(HashCodeTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

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
	public void testHashCode(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testHashCode: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test hash code", factory);
		testHashCodesEqual(factory);
		testHashCodeConsistent(factory);
		log.debug("testHashCode: Exiting - Equals is correct.");
	}

	/**
	 * Test that the hashCode logic implemented by the type the specified factory creates returns equal hashCodes for
	 * logically equivalent objects. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            hashCode logic implemented by the type returns equal hashCodes for logically equivalent objects. The
	 *            factory must create logically equivalent but different actual instances of the type upon each
	 *            invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code>, if it creates a
	 *             <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testHashCodesEqual(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testHashCodesEqual: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test hash codes equal for equal objects", factory);
		Object x = factory.create();
		Object y = factory.create();
		log.debug("testHashCodesEqual: Created objects x=[" + x + "] and y=[" + y + "] for test.");
		validationHelper.ensureExists("factory-created object", "test hash codes equal for equal objects", x);
		validationHelper.ensureExists("factory-created object", "test hash codes equal for equal objects", y);
		if (!x.equals(y)) {
			String message = "Cannot test hash codes equal for equal objects if factory does not create logically equivalent objects.";
			log.debug("testHashCodesEqual: " + message + " Throw IllegalArgumentException.");
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && x.hashCode() != y.hashCode()) {
			log.debug("testHashCodesEqual: HashCodes are not the same for equal objects.");
			AssertionUtils.fail("hashCodes are not the same for equal objects.");
		}
		log.debug("testHashCodesEqual: Exiting - Equals is correct.");
	}

	/**
	 * Test that the hashCode logic implemented by the type the specified factory creates is consistent with the
	 * <strong>consistent</strong> item of the hashCode contract. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            hashCode logic implemented by the type is consistent with the consistent item of the hashCode
	 *            contract. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testHashCodeConsistent(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testHashCodeConsistent: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test hash code consistent item", factory);
		Object x = factory.create();
		log.debug("testHashCodeConsistent: Created object x=[" + x + "] for test.");
		validationHelper.ensureExists("factory-created object", "test hash code consistent item", x);
		int hashCode = x.hashCode();
		for (int idx = 0; idx < 100; idx++) {
			if (x.hashCode() != hashCode) {
				log.debug("testHashCodeConsistent: HashCode is not consistent on invocation [" + idx + "].");
				AssertionUtils.fail("hashCode is not consistent on invocation [" + idx + "].");
			}
		}
		log.debug("testHashCodeConsistent: Exiting - Equals is correct.");
	}
}