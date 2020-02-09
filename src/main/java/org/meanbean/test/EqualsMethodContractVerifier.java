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

import org.meanbean.lang.EquivalentFactory;
import org.meanbean.util.AssertionUtils;
import org.meanbean.util.ValidationHelper;

/**
 * <p>
 * Class that affords functionality to test the equals logic implemented by objects. <br>
 * </p>
 * 
 * <p>
 * Use the tests provided by this class (namely, <code>verifyEqualsMethod()</code>) to test a class that overrides
 * <code>equals()</code>. <code>verifyEqualsMethod()</code> invokes all other tests. However, you can invoke each test
 * individually instead. <br>
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
		ValidationHelper.ensureExists("factory", "test equals", factory);
		verifyEqualsReflexive(factory);
		verifyEqualsSymmetric(factory);
		verifyEqualsTransitive(factory);
		verifyEqualsConsistent(factory);
		verifyEqualsNull(factory);
		verifyEqualsDifferentType(factory);
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>reflexive</strong> item of the equals contract. <br>
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
		ValidationHelper.ensureExists("factory", "test equals reflexive item", factory);
		Object x = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test equals reflexive item", x);
		if (!x.equals(x)) {
			AssertionUtils.fail("equals is not reflexive.");
		}
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>symmetric</strong> item of the equals contract. <br>
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
		ValidationHelper.ensureExists("factory", "test equals symmetric item", factory);
		Object x = factory.create();
		Object y = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test equals symmetric item", x);
		ValidationHelper.ensureExists("factory-created object", "test equals symmetric item", y);
		if (!x.equals(y)) {
			String message =
			        "Cannot test equals symmetric item if factory does not create logically equivalent "
			                + "objects. Does factory not create logically equivalent objects, or do objects not override "
			                + "equals?";
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && !y.equals(x)) {
			AssertionUtils.fail("equals is not symmetric.");
		}
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>transitive</strong> item of the equals contract. <br>
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
		ValidationHelper.ensureExists("factory", "test equals transitive item", factory);
		Object x = factory.create();
		Object y = factory.create();
		Object z = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test equals transitive item", x);
		ValidationHelper.ensureExists("factory-created object", "test equals transitive item", y);
		ValidationHelper.ensureExists("factory-created object", "test equals transitive item", z);
		if (!(x.equals(y) && y.equals(z))) {
			String message =
			        "Cannot test equals transitive item if factory does not create logically equivalent objects.";
			throw new IllegalArgumentException(message);
		}
		if (x.equals(y) && y.equals(z) && (!x.equals(z))) {
			AssertionUtils.fail("equals is not transitive.");
		}
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>consistent</strong> item of the equals contract. <br>
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
		ValidationHelper.ensureExists("factory", "test equals consistent item", factory);
		Object x = factory.create();
		Object y = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test equals consistent item", x);
		ValidationHelper.ensureExists("factory-created object", "test equals consistent item", y);
		for (int idx = 0; idx < 100; idx++) {
			if (!x.equals(y)) {
				AssertionUtils.fail("equals is not consistent on invocation [" + idx + "].");
			}
		}
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is consistent with the
	 * <strong>null</strong> item of the equals contract. That is, that the non-null object created by the specified
	 * factory should never be equal to a <code>null</code> object.<br>
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
		ValidationHelper.ensureExists("factory", "test equals null item", factory);
		Object x = factory.create();
		ValidationHelper.ensureExists("factory-created object", "test equals null item", x);
		if (x.equals(NULL)) {
			AssertionUtils.fail("equals is incorrect with respect to null comparison.");
		}
	}

	/**
	 * <p>
	 * Verify that the equals logic implemented by the type the specified factory creates is correct when comparing an
	 * instance of that type with an instance of an entirely different type. Two entirely different objects should never
	 * be equal to one another. <br>
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
		ValidationHelper.ensureExists("factory", "test equals for different types", factory);
		Object x = factory.create();
		Object differentObject = new Object();
		ValidationHelper.ensureExists("factory-created object", "test equals for different types", x);
		if (x.equals(differentObject)) {
			AssertionUtils.fail("equals should not find objects of different type to be equal.");
		}
	}
}
