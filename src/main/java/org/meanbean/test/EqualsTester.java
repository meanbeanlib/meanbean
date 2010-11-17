package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Class that affords functionality to test the equals logic implemented by objects. <br/>
 * 
 * Use the tests provided by this class (namely, <code>testEquals()</code>) to test a class that overrides
 * <code>equals()</code>. <code>testEquals()</code> invokes all other tests. However, you can invoke each test
 * individually instead. <br/>
 * 
 * As an example, to test the equals logic implemented by a class called MyClass do the following:
 * 
 * <pre>
 * EqualsTester equalsTester = new EqualsTester();
 * equalsTester.testEquals(new Factory<MyClass>() {
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
public class EqualsTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(EqualsTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

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
	public void testEquals(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEquals: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals", factory);
		testEqualsReflexive(factory);
		testEqualsSymmetric(factory);
		testEqualsTransitive(factory);
		testEqualsConsistent(factory);
		testEqualsNull(factory);
		testEqualsDifferentType(factory);
		log.debug("testEquals: Exiting - Equals is correct.");
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>reflexive</strong> item of the equals contract. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is consistent with the reflexive item of the equals contract. The
	 *            factory must create logically equivalent but different actual instances of the type upon each
	 *            invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEqualsReflexive(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEqualsReflexive: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals reflexive item", factory);
		Object x = factory.create();
		log.debug("testEqualsReflexive: Created object x=[" + x + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals reflexive item", x);
		if (!x.equals(x)) {
			log.debug("testEqualsReflexive: Equals is not reflexive.");
			AssertionUtils.fail("equals is not reflexive.");
		}
		log.debug("testEqualsReflexive: Exiting - Equals is reflexive.");
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>symmetric</strong> item of the equals contract. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is consistent with the symmetric item of the equals contract. The
	 *            factory must create logically equivalent but different actual instances of the type upon each
	 *            invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code>, if it creates a
	 *             <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEqualsSymmetric(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEqualsSymmetric: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals symmetric item", factory);
		Object x = factory.create();
		Object y = factory.create();
		log.debug("testEqualsSymmetric: Created objects x=[" + x + "] and y=[" + y + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals symmetric item", x);
		validationHelper.ensureExists("factory-created object", "test equals symmetric item", y);
		if (!x.equals(y)) {
			String message = "Cannot test equals symmetric item if factory does not create logically equivalent objects.";
			log.debug("testEqualsSymmetric: " + message + " Throw IllegalArgumentException.");
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && !y.equals(x)) {
			log.debug("testEqualsSymmetric: Equals is not symmetric.");
			AssertionUtils.fail("equals is not symmetric.");
		}
		log.debug("testEqualsSymmetric: Exiting - Equals is symmetric.");
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>transitive</strong> item of the equals contract. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is consistent with the transitive item of the equals contract.
	 *            The factory must create logically equivalent but different actual instances of the type upon each
	 *            invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEqualsTransitive(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEqualsTransitive: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals transitive item", factory);
		Object x = factory.create();
		Object y = factory.create();
		Object z = factory.create();
		log.debug("testEqualsTransitive: Created objects x=[" + x + "], y=[" + y + "] and z=[" + z + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals transitive item", x);
		validationHelper.ensureExists("factory-created object", "test equals transitive item", y);
		validationHelper.ensureExists("factory-created object", "test equals transitive item", z);
		if (!(x.equals(y) && y.equals(z))) {
			String message = "Cannot test equals transitive item if factory does not create logically equivalent objects.";
			log.debug("testEqualsTransitive: " + message + " Throw IllegalArgumentException.");
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && y.equals(z) && (!x.equals(z))) {
			log.debug("testEqualsTransitive: Equals is not transitive.");
			AssertionUtils.fail("equals is not transitive.");
		}
		log.debug("testEqualsTransitive: Exiting - Equals is transitive.");
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>consistent</strong> item of the equals contract. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates non-null logically equivalent objects that will be used to test whether the
	 *            equals logic implemented by the type is consistent with the consistent item of the equals contract.
	 *            The factory must create logically equivalent but different actual instances of the type upon each
	 *            invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEqualsConsistent(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEqualsConsistent: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals consistent item", factory);
		Object x = factory.create();
		Object y = factory.create();
		log.debug("testEqualsConsistent: Created objects x=[" + x + "] and y=[" + y + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals consistent item", x);
		validationHelper.ensureExists("factory-created object", "test equals consistent item", y);
		for (int idx = 0; idx < 100; idx++) {
			if (!x.equals(y)) {
				log.debug("testEqualsConsistent: Equals is not consistent on invocation [" + idx + "].");
				AssertionUtils.fail("equals is not consistent on invocation [" + idx + "].");
			}
		}
		log.debug("testEqualsConsistent: Exiting - Equals is consistent.");
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>null</strong> item of the equals contract. That is, that the non-null object created by the specified
	 * factory should never be equal to a <code>null</code> object.<br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates a non-null object that will be used to test whether the equals logic
	 *            implemented by the type is consistent with the null item of the equals contract. The factory must
	 *            create a non-null instance of the type upon each invocation of <code>create()</code> in order for the
	 *            test to function.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEqualsNull(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEqualsNull: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals null item", factory);
		Object x = factory.create();
		log.debug("testEqualsNull: Created object x=[" + x + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals null item", x);
		if (x.equals(null)) {
			log.debug("testEqualsNull: Equals is incorrect with respect to null comparison.");
			AssertionUtils.fail("equals is incorrect with respect to null comparison.");
		}
		log.debug("testEqualsNull: Exiting - Equals is correct with respect to null comparison.");
	}

	/**
	 * Test that the equals logic implemented by the type the specified factory creates is correct when comparing an
	 * instance of that type with an instance of an entirely different type. Two entirely different objects should never
	 * be equal to one another. <br/>
	 * 
	 * If the test fails, an AssertionError is thrown.
	 * 
	 * @param factory
	 *            A Factory that creates a non-null object that will be used to test whether the equals logic
	 *            implemented by the type is correct when comparing itself with an instance of an entirely different
	 *            type. The factory must create a non-null instance of the type upon each invocation of
	 *            <code>create()</code> in order for the test to function.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void testEqualsDifferentType(Factory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testEqualsDifferentType: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test equals for different types", factory);
		Object x = factory.create();
		Object differentObect = new Object();
		log.debug("testEqualsDifferentType: Created object x=[" + x + "] and differentObject=[" + differentObect
		        + "] for test.");
		validationHelper.ensureExists("factory-created object", "test equals for different types", x);
		if (x.equals(differentObect)) {
			log.debug("testEqualsDifferentType: Equals is incorrect as it found objects of different type to be equal.");
			AssertionUtils.fail("equals should not find objects of different type to be equal.");
		}
		log.debug("testEqualsDifferentType: Exiting - Equals is correct when comparing an object of different type.");
	}
}