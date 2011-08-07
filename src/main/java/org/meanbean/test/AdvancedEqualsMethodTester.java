package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformationException;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.basic.EquivalentEnumFactory;
import org.meanbean.factories.beans.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * <p>
 * Concrete implementation of the SmartEqualsMethodTester that provides a means of testing the correctness of the equals
 * logic implemented by a type, based solely on the type, with respect to:
 * </p>
 * 
 * <ul>
 * <li>the general equals contract</li>
 * 
 * <li>the programmer's expectation of property significance in object equality</li>
 * </ul>
 * 
 * <p>
 * This implementation wraps an EqualsMethodTester implementation, delegating to it, decorating it with an improved and
 * simplified API that does not require the provision of a Factory implementation to test a type. Rather, only the type
 * to test need be specified.
 * </p>
 * 
 * <p>
 * The following is tested:
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
 * <p>
 * As an example, to test the equals logic implemented by a class called MyClass do the following:
 * </p>
 * 
 * <pre>
 * SmartEqualsMethodTester tester = new AdvancedEqualsMethodTester();
 * tester.testEqualsMethod(MyClass.class);
 * </pre>
 * 
 * <p>
 * In the above example all properties are assumed to be considered by MyClass's equals logic.
 * </p>
 * 
 * <p>
 * The following example tests the equals logic implemented by a class called MyComplexClass which has two properties:
 * firstName and lastName. Only firstName is considered in the equals logic. Therefore, lastName is specified in the
 * insignificantProperties varargs:
 * </p>
 * 
 * <pre>
 * SmartEqualsMethodTester tester = new AdvancedEqualsMethodTester();
 * tester.testEqualsMethod(MyComplexClass.class, &quot;lastName&quot;);
 * </pre>
 * 
 * @author Graham Williamson
 */
public class AdvancedEqualsMethodTester implements SmartEqualsMethodTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(AdvancedEqualsMethodTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** Tester that will be delegated to. */
	private final EqualsMethodTester equalsMethodTester;

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	/**
	 * <p>
	 * Construct a new Advanced Equals Method Tester that provides a means of testing the correctness of the equals
	 * logic implemented by a type, based solely on the type.
	 * </p>
	 * 
	 * <p>
	 * This implementation wraps an EqualsMethodTester implementation, delegating to it, decorating it with an improved
	 * and simplified API that does not require the provision of a Factory implementation to test a type. Rather, only
	 * the type to test need be specified.
	 * </p>
	 * 
	 * <p>
	 * This constructor configures the Advanced Equals Method Tester to use the default
	 * <code>BasicEqualsMethodTester</code> logic.
	 * </p>
	 */
	public AdvancedEqualsMethodTester() {
		this(new BasicEqualsMethodTester());
	}

	/**
	 * <p>
	 * Construct a new Advanced Equals Method Tester that provides a means of testing the correctness of the equals
	 * logic implemented by a type, based solely on the type.
	 * </p>
	 * 
	 * <p>
	 * This implementation wraps an EqualsMethodTester implementation, delegating to it, decorating it with an improved
	 * and simplified API that does not require the provision of a Factory implementation to test a type. Rather, only
	 * the type to test need be specified.
	 * </p>
	 * 
	 * <p>
	 * This constructor configures the Advanced Equals Method Tester to use the specified EqualsMethodTester
	 * implementation.
	 * </p>
	 * 
	 * @param equalsMethodTester
	 *            The <code>EqualsMethodTester</code> logic to use to test the equals logic of a type.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified EqualsMethodTester is deemed illegal. For example, if it is <code>null</code>.
	 */
	public AdvancedEqualsMethodTester(EqualsMethodTester equalsMethodTester) throws IllegalArgumentException {
		log.debug("AdvancedEqualsMethodTester: Entering with equalsMethodTester=[" + equalsMethodTester + "].");
		validationHelper.ensureExists("equalsMethodTester", "construct smart equals method tester", equalsMethodTester);
		this.equalsMethodTester = equalsMethodTester;
		log.debug("AdvancedEqualsMethodTester: Exiting.");
	}

	/**
	 * <p>
	 * Test that the equals logic implemented by the type specified is correct by testing:
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
	 * <li>that an entirely different type of object is not deemed equal to an object of the specified type</li>
	 * 
	 * <li>that the equality of an object <strong>is not</strong> affected by properties that <strong>are not</strong>
	 * considered in the equality logic</li>
	 * 
	 * <li>that the equality of an object <strong>is</strong> affected by properties that <strong>are</strong>
	 * considered in the equality logic</li>
	 * </ul>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param clazz
	 *            The type to test the equals logic of.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified clazz or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>. Also, if any of the specified insignificantProperties do not exist on the class
	 *             under test.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	@Override
	public void testEqualsMethod(Class<?> clazz, String... insignificantProperties) throws IllegalArgumentException,
	        BeanInformationException, BeanTestException, AssertionError {
		testEqualsMethod(clazz, null, insignificantProperties);
	}

	/**
	 * <p>
	 * Test that the equals logic implemented by the type specified is correct by testing:
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
	 * <li>that an entirely different type of object is not deemed equal to an object of the specified type</li>
	 * 
	 * <li>that the equality of an object <strong>is not</strong> affected by properties that <strong>are not</strong>
	 * considered in the equality logic</li>
	 * 
	 * <li>that the equality of an object <strong>is</strong> affected by properties that <strong>are</strong>
	 * considered in the equality logic</li>
	 * </ul>
	 * 
	 * <p>
	 * If the test fails, an AssertionError is thrown.
	 * </p>
	 * 
	 * @param clazz
	 *            The type to test the equals logic of.
	 * @param customConfiguration
	 *            A custom Configuration to be used when testing to ignore the testing of named properties or use a
	 *            custom test data Factory when testing a named property. This Configuration is only used for this
	 *            individual test and will not be retained for future testing of this or any other type. If no custom
	 *            Configuration is required, pass <code>null</code> or use
	 *            <code>testEqualsMethod(Class<?>,String...)</code> instead.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified clazz or insignificantProperties are deemed illegal. For example, if either
	 *             is <code>null</code>. Also, if any of the specified insignificantProperties do not exist on the class
	 *             under test.
	 * @throws BeanInformationException
	 *             If a problem occurs when trying to obtain information about the type to test.
	 * @throws BeanTestException
	 *             If a problem occurs when testing the type, such as an inability to read or write a property of the
	 *             type to test.
	 * @throws AssertionError
	 *             If the test fails.
	 */
	@Override
	public void testEqualsMethod(Class<?> clazz, Configuration customConfiguration, String... insignificantProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError {
		log.debug("testEqualsMethod: Entering with clazz=[" + clazz + "], customConfiguration=[" + customConfiguration
		        + "], insignificantProperties=[" + insignificantProperties + "].");
		validationHelper.ensureExists("clazz", "test equals method", clazz);
		Factory<?> factory = createFactory(clazz);
		equalsMethodTester.testEqualsMethod(factory, customConfiguration, insignificantProperties);
		log.debug("testEqualsMethod: Exiting - Equals is correct.");
	}

	private Factory<?> createFactory(Class<?> clazz) {
		if (classIsAnEnum(clazz)) {
			return createEnumClassFactory(clazz);
		} else {
			return createPopulatedBeanFactory(clazz);
		}
	}

	private boolean classIsAnEnum(Class<?> clazz) {
		return clazz.isEnum();
	}

	private EquivalentEnumFactory createEnumClassFactory(Class<?> clazz) {
		return new EquivalentEnumFactory(clazz);
	}

	private EquivalentPopulatedBeanFactory createPopulatedBeanFactory(Class<?> clazz) {
		return new EquivalentPopulatedBeanFactory(beanInformationFactory.create(clazz),
		        equalsMethodTester.getFactoryLookupStrategy());
	}

	/**
	 * Get a RandomValueGenerator.
	 * 
	 * @return A RandomValueGenerator.
	 */
	@Override
	public RandomValueGenerator getRandomValueGenerator() {
		return equalsMethodTester.getRandomValueGenerator();
	}

	/**
	 * Get the collection of test data Factories with which you can register new Factories for custom Data Types.
	 * 
	 * @return The collection of test data Factories.
	 */
	@Override
	public FactoryCollection getFactoryCollection() {
		return equalsMethodTester.getFactoryCollection();
	}

	/**
	 * Get the FactoryLookupStrategy, which provides a means of acquiring Factories.
	 * 
	 * @return The factory lookup strategy.
	 */
	@Override
	public FactoryLookupStrategy getFactoryLookupStrategy() {
		return equalsMethodTester.getFactoryLookupStrategy();
	}
}