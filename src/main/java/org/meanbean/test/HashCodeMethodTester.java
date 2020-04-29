/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

package org.meanbean.test;

import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.equivalent.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceFactory;
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

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();

	/** The collection of test data Factories. */
	private final FactoryCollection factoryCollection = FactoryCollection.getInstance();

	/** Provides a means of acquiring a suitable Factory. */
	private final FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	/**
	 * Prefer {@link BeanVerifier}
	 */
	public HashCodeMethodTester() {
		
	}
	
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
        ServiceFactory.inScope(() -> doTestHashCodeMethod(factory));
	}

    private void doTestHashCodeMethod(EquivalentFactory<?> factory) throws IllegalArgumentException, AssertionError {
		ValidationHelper.ensureExists("factory", "test hash code method", factory);
		testHashCodesEqual(factory);
		testHashCodeConsistent(factory);
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
		ValidationHelper.ensureExists("clazz", "test hash code method", clazz);
		EquivalentPopulatedBeanFactory factory =
		        new EquivalentPopulatedBeanFactory(beanInformationFactory.create(clazz), getFactoryLookupStrategy());
		testHashCodeMethod(factory);
	}

	/**
	 * <p>
	 * Test that the hashCode logic implemented by the type the specified factory creates returns equal hashCodes for
	 * logically equivalent objects. <br>
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
		ValidationHelper.ensureExists("factory", "test hash codes equal for equal objects", factory);
		Object x = factory.create();
		Object y = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test hash codes equal for equal objects", x);
		ValidationHelper.ensureExists("factory-created object", "test hash codes equal for equal objects", y);
		if (!x.equals(y)) {
			String message =
			        "Cannot test hash codes equal for equal objects if objects that should be equal are not considered logically equivalent.";
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && x.hashCode() != y.hashCode()) {
			AssertionUtils.fail("hashCodes are not the same for equal objects.");
		}
	}

	/**
	 * <p>
	 * Test that the hashCode logic implemented by the type the specified factory creates is consistent with the
	 * <strong>consistent</strong> item of the hashCode contract. <br>
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
		ValidationHelper.ensureExists("factory", "test hash code consistent item", factory);
		Object x = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test hash code consistent item", x);
		int hashCode = x.hashCode();
		for (int idx = 0; idx < 100; idx++) {
			if (x.hashCode() != hashCode) {
				AssertionUtils.fail("hashCode is not consistent on invocation [" + idx + "].");
			}
		}
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
