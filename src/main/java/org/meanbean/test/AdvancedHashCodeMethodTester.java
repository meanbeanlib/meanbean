package org.meanbean.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.beans.EquivalentPopulatedBeanFactory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * <p>
 * Concrete implementation of the SmartHashCodeMethodTester that provides a means of testing the correctness of the
 * hashCode logic implemented by a type, based solely on the provision of the type, with respect to:
 * </p>
 * 
 * <ul>
 * <li>the general hashCode contract</li>
 * </ul>
 * 
 * <p>
 * This implementation wraps a HashCodeMethodTester implementation, delegating to it, decorating it with an improved and
 * simplified API that does not require the provision of a Factory implementation to test a type. Rather, only the type
 * to test need be specified.
 * </p>
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
public class AdvancedHashCodeMethodTester {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(AdvancedHashCodeMethodTester.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** Tester that will be delegated to. */
	private final HashCodeMethodTester hashCodeMethodTester;

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	/**
	 * <p>
	 * Construct a new Advanced Hash Code Method Tester that provides a means of testing the correctness of the hashCode
	 * logic implemented by a type, based solely on the type.
	 * </p>
	 * 
	 * <p>
	 * This implementation wraps a HashCodeMethodTester implementation, delegating to it, decorating it with an improved
	 * and simplified API that does not require the provision of a Factory implementation to test a type. Rather, only
	 * the type to test need be specified.
	 * </p>
	 * 
	 * <p>
	 * This constructor configures the Advanced Hash Code Method Tester to use the default
	 * <code>BasicHashCodeMethodTester</code> logic.
	 * </p>
	 */
	public AdvancedHashCodeMethodTester() {
		this(new HashCodeMethodTester());
	}

	/**
	 * <p>
	 * Construct a new Advanced Hash Code Method Tester that provides a means of testing the correctness of the hashCode
	 * logic implemented by a type, based solely on the type.
	 * </p>
	 * 
	 * <p>
	 * This implementation wraps a HashCodeMethodTester implementation, delegating to it, decorating it with an improved
	 * and simplified API that does not require the provision of a Factory implementation to test a type. Rather, only
	 * the type to test need be specified.
	 * </p>
	 * 
	 * <p>
	 * This constructor configures the Advanced Hash Code Method Tester to use the specified HashCodeMethodTester
	 * implementation.
	 * </p>
	 * 
	 * @param hashCodeMethodTester
	 *            The <code>HashCodeMethodTester</code> logic to use to test the hashCode logic of a type.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified HashCodeMethodTester is deemed illegal. For example, if it is <code>null</code>.
	 */
	public AdvancedHashCodeMethodTester(HashCodeMethodTester hashCodeMethodTester) {
		log.debug("AdvancedHashCodeMethodTester: Entering with hashCodeMethodTester=[" + hashCodeMethodTester + "].");
		validationHelper.ensureExists("hashCodeMethodTester", "construct smart hash code method tester",
		        hashCodeMethodTester);
		this.hashCodeMethodTester = hashCodeMethodTester;
		log.debug("AdvancedHashCodeMethodTester: Exiting.");
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
		        new EquivalentPopulatedBeanFactory(beanInformationFactory.create(clazz),
		                hashCodeMethodTester.getFactoryLookupStrategy());
		hashCodeMethodTester.testHashCodeMethod(factory);
		log.debug("testHashCodeMethod: Exiting - HashCode is correct.");
	}
}