package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.factories.equivalent.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.BasicFactoryLookupStrategy;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * <p>
 * Provides a means of testing the correctness of the hashCode logic implemented by a type, based solely on the
 * provision of the type, with respect to:
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
 * <p>
 * Use the tests provided by this class (namely, <code>testHashCode()</code>) to test a class that overrides
 * <code>hashCode()</code> and <code>equals()</code>.
 * </p>
 * 
 * <p>
 * As an example, to test the hashCode logic implemented by a class called MyClass do the following:
 * </p>
 * 
 * <pre>
 * HashCodeMethodTester tester = new HashCodeMethodTester();
 * tester.testHashCodeMethod(MyClass.class);
 * </pre>
 * 
 * <p>
 * To test the hashCode logic implemented by a class called MyClass without a no-argument constructor do the following:
 * </p>
 * 
 * <pre>
 * HashCodeMethodTester tester = new HashCodeMethodTester();
 * tester.testHashCodeMethod(new Factory&lt;MyClass&gt;() {
 * 	&#064;Override
 * 	public MyClass create() {
 * 		MyClass result = new MyClass(&quot;TEST_NAME&quot;);
 * 		return result;
 * 	}
 * });
 * </pre>
 * 
 * <p>
 * The Factory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>. In the above example, there is only one property, name, which is
 * considered by MyClass's hashCode logic.
 * </p>
 * 
 * @author Graham Williamson
 */
public class HashCodeMethodTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(HashCodeMethodTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection = new FactoryRepository(randomValueGenerator);

	/** Provides a means of acquiring a suitable Factory. */
	private final FactoryLookupStrategy factoryLookupStrategy = new BasicFactoryLookupStrategy(factoryCollection,
	        randomValueGenerator);

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	/**
	 * <p>
	 * Test that the hashCode logic implemented by the type the specified factory creates is correct by testing:
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
	public void testHashCodeMethod(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testHashCodeMethod: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test hash code method", factory);
		testHashCodesEqual(factory);
		testHashCodeConsistent(factory);
		log.debug("testHashCodeMethod: Exiting - Equals is correct.");
	}

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
	public void testHashCodeMethod(Class<?> clazz) throws IllegalArgumentException, AssertionError {
		log.debug("testHashCodeMethod: Entering with clazz=[" + clazz + "].");
		validationHelper.ensureExists("clazz", "test hash code method", clazz);
		EquivalentPopulatedBeanFactory factory =
		        new EquivalentPopulatedBeanFactory(beanInformationFactory.create(clazz), getFactoryLookupStrategy());
		testHashCodeMethod(factory);
		log.debug("testHashCodeMethod: Exiting - HashCode is correct.");
	}

	/**
	 * <p>
	 * Test that the hashCode logic implemented by the type the specified factory creates returns equal hashCodes for
	 * logically equivalent objects. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the hashCode logic implemented by the type returns equal hashCodes for logically equivalent
	 *            objects. The factory must create logically equivalent but different actual instances of the type upon
	 *            each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code>, if it creates a
	 *             <code>null</code> object or if it creates objects that are not logically equivalent.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	protected void testHashCodesEqual(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		log.debug("testHashCodesEqual: Entering with factory=[" + factory + "].");
		validationHelper.ensureExists("factory", "test hash codes equal for equal objects", factory);
		Object x = factory.create();
		Object y = factory.create();
		log.debug("testHashCodesEqual: Created objects x=[" + x + "] and y=[" + y + "] for test.");
		validationHelper.ensureExists("factory-created object", "test hash codes equal for equal objects", x);
		validationHelper.ensureExists("factory-created object", "test hash codes equal for equal objects", y);
		if (!x.equals(y)) {
			String message =
			        "Cannot test hash codes equal for equal objects if objects that should be equal are not considered logically equivalent.";
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
	 * <p>
	 * Test that the hashCode logic implemented by the type the specified factory creates is consistent with the
	 * <strong>consistent</strong> item of the hashCode contract. <br/>
	 * </p>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the hashCode logic implemented by the type is consistent with the consistent item of the
	 *            hashCode contract. The factory must create logically equivalent but different actual instances of the
	 *            type upon each invocation of <code>create()</code> in order for the test to be meaningful.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified factory is deemed illegal. For example, if it is <code>null</code> or if it creates
	 *             a <code>null</code> object.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	protected void testHashCodeConsistent(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
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

	/**
	 * Get a RandomValueGenerator.
	 * 
	 * @return A RandomValueGenerator.
	 */
	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	/**
	 * Get the collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	public FactoryCollection getFactoryCollection() {
		return factoryCollection;
	}

	/**
	 * Get the FactoryLookupStrategy, which provides a means of acquiring Factories.
	 * 
	 * @return The factory lookup strategy.
	 */
	public FactoryLookupStrategy getFactoryLookupStrategy() {
		return factoryLookupStrategy;
	}
}