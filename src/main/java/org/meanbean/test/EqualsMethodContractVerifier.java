package org.meanbean.test;

import org.meanbean.lang.EquivalentFactory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;

/**
 * <p>
 * Class that affords functionality to test the equals logic implemented by objects. <br/>
 * </p>
 * 
 * <p>
 * Use the tests provided by this class (namely, <code>verifyEqualsMethod()</code>) to test a class that overrides
 * <code>equals()</code>. <code>verifyEqualsMethod()</code> invokes all other tests. However, you can invoke each test
 * individually instead. <br/>
 * </p>
 * 
 * <p>
 * As an example, to test the equals logic implemented by a class called MyClass do the following:
 * </p>
 * 
 * <pre>
 * EqualsMethodContractVerifier verifier = new EqualsMethodContractVerifier();
 * verifier.verifyEqualsMethod(new EquivalentFactory<MyClass>() {
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
 * <p>
 * The EquivalentFactory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>.
 * </p>
 * 
 * @author Graham Williamson
 */
class EqualsMethodContractVerifier {

	/** Logging mechanism. */
	private static final $Logger logger = $LoggerFactory.getLogger(EqualsMethodContractVerifier.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(logger);

	/** Null value */
	private static final Object NULL = null;

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is correct by testing:
	 * </p>
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
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is correct. The factory must create logically
	 *            equivalent but different actual instances of the type upon each invocation of <code>create()</code> in
	 *            order for the test to be meaningful and correct.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code>, if it creates a
	 *             <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void verifyEqualsMethod(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsMethod: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals", factory);
		verifyEqualsReflexive(factory);
		verifyEqualsSymmetric(factory);
		verifyEqualsTransitive(factory);
		verifyEqualsConsistent(factory);
		verifyEqualsNull(factory);
		verifyEqualsDifferentType(factory);
		logger.debug("verifyEqualsMethod: Exiting - Equals is correct.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>reflexive</strong> item of the equals contract. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is consistent with the reflexive item of the equals
	 *            contract. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void verifyEqualsReflexive(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsReflexive: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals reflexive item", factory);
		Object x = factory.create();
		logger.debug("verifyEqualsReflexive: Created object x=[{}] for test.", x);
		validationHelper.ensureExists("factory-created object", "test equals reflexive item", x);
		if (!x.equals(x)) {
			logger.debug("verifyEqualsReflexive: Equals is not reflexive.");
			AssertionUtils.fail("equals is not reflexive.");
		}
		logger.debug("verifyEqualsReflexive: Exiting - Equals is reflexive.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>symmetric</strong> item of the equals contract. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is consistent with the symmetric item of the equals
	 *            contract. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code>, if it creates a
	 *             <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void verifyEqualsSymmetric(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsSymmetric: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals symmetric item", factory);
		Object x = factory.create();
		Object y = factory.create();
		logger.debug("verifyEqualsSymmetric: Created objects x=[{}] and y=[{}] for test.", x, y);
		validationHelper.ensureExists("factory-created object", "test equals symmetric item", x);
		validationHelper.ensureExists("factory-created object", "test equals symmetric item", y);
		if (!x.equals(y)) {
			String message =
			        "Cannot test equals symmetric item if factory does not create logically equivalent "
			                + "objects. Does factory not create logically equivalent objects, or do objects not override "
			                + "equals?";
			logger.debug("verifyEqualsSymmetric: {} Throw IllegalArgumentException.", message);
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && !y.equals(x)) {
			logger.debug("verifyEqualsSymmetric: Equals is not symmetric.");
			AssertionUtils.fail("equals is not symmetric.");
		}
		logger.debug("verifyEqualsSymmetric: Exiting - Equals is symmetric.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>transitive</strong> item of the equals contract. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is consistent with the transitive item of the equals
	 *            contract. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void verifyEqualsTransitive(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsTransitive: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals transitive item", factory);
		Object x = factory.create();
		Object y = factory.create();
		Object z = factory.create();
		logger.debug("verifyEqualsTransitive: Created objects x=[{}], y=[{}] and z=[{}] for test.", x, y,z);
		validationHelper.ensureExists("factory-created object", "test equals transitive item", x);
		validationHelper.ensureExists("factory-created object", "test equals transitive item", y);
		validationHelper.ensureExists("factory-created object", "test equals transitive item", z);
		if (!(x.equals(y) && y.equals(z))) {
			String message =
			        "Cannot test equals transitive item if factory does not create logically equivalent objects.";
			logger.debug("verifyEqualsTransitive: {} Throw IllegalArgumentException.", message);
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && y.equals(z) && (!x.equals(z))) {
			logger.debug("verifyEqualsTransitive: Equals is not transitive.");
			AssertionUtils.fail("equals is not transitive.");
		}
		logger.debug("verifyEqualsTransitive: Exiting - Equals is transitive.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>consistent</strong> item of the equals contract. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is consistent with the consistent item of the equals
	 *            contract. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	public void verifyEqualsConsistent(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsConsistent: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals consistent item", factory);
		Object x = factory.create();
		Object y = factory.create();
		logger.debug("verifyEqualsConsistent: Created objects x=[{}] and y=[{}] for test.", x,y);
		validationHelper.ensureExists("factory-created object", "test equals consistent item", x);
		validationHelper.ensureExists("factory-created object", "test equals consistent item", y);
		for (int idx = 0; idx < 100; idx++) {
			if (!x.equals(y)) {
				logger.debug("verifyEqualsConsistent: Equals is not consistent on invocation [{}].", idx);
				AssertionUtils.fail("equals is not consistent on invocation [" + idx + "].");
			}
		}
		logger.debug("verifyEqualsConsistent: Exiting - Equals is consistent.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>null</strong> item of the equals contract. That is, that the non-null object created by the specified
	 * factory should never be equal to a <code>null</code> object.<br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates a non-null object that will be used to test whether the equals logic
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
	public void verifyEqualsNull(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsNull: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals null item", factory);
		Object x = factory.create();
		logger.debug("verifyEqualsNull: Created object x=[{}] for test.", x);
		validationHelper.ensureExists("factory-created object", "test equals null item", x);
		if (x.equals(NULL)) {
			logger.debug("verifyEqualsNull: Equals is incorrect with respect to null comparison.");
			AssertionUtils.fail("equals is incorrect with respect to null comparison.");
		}
		logger.debug("verifyEqualsNull: Exiting - Equals is correct with respect to null comparison.");
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is correct when comparing an
	 * instance of that type with an instance of an entirely different type. Two entirely different objects should never
	 * be equal to one another. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates a non-null object that will be used to test whether the equals logic
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
	public void verifyEqualsDifferentType(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		logger.debug("verifyEqualsDifferentType: Entering with factory=[{}].", factory);
		validationHelper.ensureExists("factory", "test equals for different types", factory);
		Object x = factory.create();
		Object differentObject = new Object();
		logger.debug("verifyEqualsDifferentType: Created object x=[{}] and differentObject=[{}] for test.", x, differentObject);
		validationHelper.ensureExists("factory-created object", "test equals for different types", x);
		if (x.equals(differentObject)) {
			logger.debug("verifyEqualsDifferentType: Equals is incorrect as it found objects of different type to be equal.");
			AssertionUtils.fail("equals should not find objects of different type to be equal.");
		}
		logger.debug("verifyEqualsDifferentType: Exiting - Equals is correct when comparing an object of different type.");
	}
}