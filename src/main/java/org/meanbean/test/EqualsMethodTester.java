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

import org.meanbean.bean.info.BeanInformationException;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.equivalent.EquivalentEnumFactory;
import org.meanbean.factories.equivalent.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.util.ServiceFactory;
import org.meanbean.util.ValidationHelper;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * Provides a means of testing the correctness of the equals logic implemented by a type, based solely on the type, with
 * respect to:
 * </p>
 * 
 * <ul>
 * <li>the general equals contract</li>
 * 
 * <li>the programmer's expectation of property significance in object equality</li>
 * </ul>
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
 * To test the equals logic implemented by a class called MyClass do the following:
 * </p>
 * 
 * <pre>
 * EqualsMethodTester tester = new EqualsMethodTester();
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
 * EqualsMethodTester tester = new EqualsMethodTester();
 * tester.testEqualsMethod(MyComplexClass.class, &quot;lastName&quot;);
 * </pre>
 * 
 * <p>
 * In order for the above to work successfully, the class under test must have a no-argument constructor. If this is not
 * the case, you must provide an EquivalentFactory implementation:
 * </p>
 * 
 * <pre>
 * EqualsMethodTester tester = new EqualsMethodTester();
 * tester.testEqualsMethod(new EquivalentFactory&lt;MyClass&gt;() {
 * 	&#064;Override
 * 	public MyClass create() {
 * 		MyClass result = new MyClass();
 * 		// initialize result...
 * 		result.setName(&quot;TEST_NAME&quot;);
 * 		return result;
 * 	}
 * });
 * </pre>
 * 
 * <p>
 * The Factory creates <strong>new logically equivalent</strong> instances of MyClass. MyClass has overridden
 * <code>equals()</code> and <code>hashCode()</code>. In the above example, there is only one property, name, which is
 * considered by MyClass's equals logic.
 * </p>
 * 
 * <p>
 * The following example tests the equals logic implemented by a class called MyComplexClass which has two properties:
 * firstName and lastName, but lacks a no-argument constructor. Only firstName is considered in the equals logic.
 * Therefore, lastName is specified in the insignificantProperties varargs:
 * </p>
 * 
 * <pre>
 * EqualsMethodTester tester = new EqualsMethodTester();
 * tester.testEqualsMethod(new EquivalentFactory&lt;MyComplexClass&gt;() {
 * 	&#064;Override
 * 	public MyComplexClass create() {
 * 		MyComplexClass result = new MyComplexClass(&quot;TEST_FIRST_NAME&quot;, &quot;TEST_LAST_NAME&quot;);
 * 		return result;
 * 	}
 * }, &quot;lastName&quot;);
 * </pre>
 * 
 * @author Graham Williamson
 */
public class EqualsMethodTester {

	/** Factory used to gather information about a given bean and store it in a BeanInformation object. */
	private final BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	/** The verifier to which general contract verification is delegated. */
	private final EqualsMethodContractVerifier contractVerifier = new EqualsMethodContractVerifier();

	/** The verifier to which property significance verification is delegated. */
	private final EqualsMethodPropertySignificanceVerifier propertySignificanceVerifier =
	        new PropertyBasedEqualsMethodPropertySignificanceVerifier();

	private final Configuration defaultConfiguration;
	private final Map<Class<?>, Configuration> customConfigurations;

	/**
	 * Prefer {@link BeanVerification}
	 */
	public EqualsMethodTester() {
		this(Collections.emptyMap(), Configuration.defaultConfiguration());
	}

	EqualsMethodTester(Map<Class<?>, Configuration> customConfigurations, Configuration defaultConfiguration) {
		this.customConfigurations = customConfigurations;
		this.defaultConfiguration = defaultConfiguration;
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
	 *            <code>testEqualsMethod(Class&lt;?&gt;,String...)</code> instead.
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
	public void testEqualsMethod(Class<?> clazz, Configuration customConfiguration, String... insignificantProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError {
		ValidationHelper.ensureExists("clazz", "test equals method", clazz);
		EquivalentFactory<?> factory = createEquivalentFactory(clazz);
		customConfiguration = getEffectiveConfiguration(clazz, customConfiguration);
		testEqualsMethod(factory, customConfiguration, insignificantProperties);
	}

	/**
	 * <p>
	 * Test that the equals logic implemented by the type the specified factory creates is correct by testing:
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
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is correct. The factory must create logically
	 *            equivalent but different actual instances of the type upon each invocation of <code>create()</code> in
	 *            order for the test to be meaningful and correct.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
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
	public void testEqualsMethod(EquivalentFactory<?> factory, String... insignificantProperties)
	        throws IllegalArgumentException, BeanInformationException, BeanTestException, AssertionError {
		testEqualsMethod(factory, null, insignificantProperties);
	}

	/**
	 * <p>
	 * Test that the equals logic implemented by the type the specified factory creates is correct by testing:
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
	 * @param factory
	 *            An EquivalentFactory that creates non-null logically equivalent objects that will be used to test
	 *            whether the equals logic implemented by the type is correct. The factory must create logically
	 *            equivalent but different actual instances of the type upon each invocation of <code>create()</code> in
	 *            order for the test to be meaningful and correct.
	 * @param customConfiguration
	 *            A custom Configuration to be used when testing to ignore the testing of named properties or use a
	 *            custom test data Factory when testing a named property. This Configuration is only used for this
	 *            individual test and will not be retained for future testing of this or any other type. If no custom
	 *            Configuration is required, pass <code>null</code> or use
	 *            <code>testEqualsMethod(Factory&lt;?&gt;,String...)</code> instead.
	 * @param insignificantProperties
	 *            The names of properties that are not used when deciding whether objects are logically equivalent. For
	 *            example, "lastName".
	 * 
	 * @throws IllegalArgumentException
	 *             If either the specified factory or insignificantProperties are deemed illegal. For example, if either
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
    public void testEqualsMethod(EquivalentFactory<?> factory, Configuration customConfiguration,
            String... insignificantProperties) throws IllegalArgumentException, BeanInformationException,
            BeanTestException, AssertionError {
        ServiceFactory.inScope(() -> doTestEqualsMethod(factory, customConfiguration, insignificantProperties));
    }

    private void doTestEqualsMethod(EquivalentFactory<?> factory, Configuration customConfiguration,
            String... insignificantProperties) throws IllegalArgumentException, BeanInformationException,
            BeanTestException, AssertionError {
		ValidationHelper.ensureExists("factory", "test equals method", factory);
		ValidationHelper.ensureExists("insignificantProperties", "test equals method", insignificantProperties);
		insignificantProperties = insignificantProperties == null || insignificantProperties.length == 0
				? defaultConfiguration.getEqualsInsignificantProperties().toArray(new String[0])
				: insignificantProperties;

		contractVerifier.verifyEqualsReflexive(factory);
		contractVerifier.verifyEqualsSymmetric(factory);
		contractVerifier.verifyEqualsTransitive(factory);
		contractVerifier.verifyEqualsConsistent(factory);
		contractVerifier.verifyEqualsNull(factory);
		contractVerifier.verifyEqualsDifferentType(factory);

		customConfiguration = getEffectiveConfiguration(null, customConfiguration);
		// Override the standard number of iterations if need be
		int iterations = defaultConfiguration.getIterations();
		if (customConfiguration.hasIterationsOverride()) {
			iterations = customConfiguration.getIterations();
		}
		
		// Test property significance 'iterations' times
		for (int idx = 0; idx < iterations; idx++) {
			propertySignificanceVerifier.verifyEqualsMethod(factory, customConfiguration, insignificantProperties);
		}
	}
	
	private Configuration getEffectiveConfiguration(Class<?> beanClass, Configuration configuration) {
		if (configuration != null) {
			return configuration;
		}
		if (beanClass != null && customConfigurations.containsKey(beanClass)) {
			return customConfigurations.get(beanClass);
		}
		return defaultConfiguration;
	}

	private EquivalentFactory<?> createEquivalentFactory(Class<?> clazz) {
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
		FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();
		return new EquivalentPopulatedBeanFactory(beanInformationFactory.create(clazz), factoryLookupStrategy);
	}
}
