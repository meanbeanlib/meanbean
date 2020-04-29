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

import org.meanbean.util.ClassPathUtils;
import org.meanbean.util.ServiceFactory;

import java.util.function.Consumer;

/**
 * BeanVerifier can be used in unit tests to verify
 * <ul> 
 * <li> bean getter/setter methods </li>
 * <li> equals/hashCode methods </li>
 * <li> toString method </li>
 * </ul>
 * 
 * Example usage:
 * <pre>
 * BeanVerifier.verifyBean(Company.class); // verify bean methods, equals/hashCode and toString
 * 
 * BeanVerifier.verifyBeans(Company.class, Employee.class);
 * 
 * BeanVerifier.forClass(Company.class)
 *     .withSettings(settings -&gt; settings.setDefaultIterations(12))
 *     .withSettings(settings -&gt; settings.addIgnoredProperty(Company::getName)) // exclude name property in bean getter/setter test
 *     .verifyGettersAndSetters()
 * </pre>
 */
public interface BeanVerifier {

	/**
	 * Start a fluent bean verification chain for the given beanClass 
	 */
	public static BeanVerifier forClass(Class<?> beanClass) {
		return new BeanVerifierImpl(beanClass);
	}

	/**
	 * Verify that given beanClass has valid bean getters/setters, equals/hashCode, and toString methods
	 */
	public static void verifyBean(Class<?> beanClass) {
		forClass(beanClass).verify();
	}

	/**
	 * Verify that given beanClasses have valid bean getters/setters, equals/hashCode, and toString methods
	 */
	public static void verifyBeans(Class<?>... beanClasses) {
		for (Class<?> beanClass : beanClasses) {
			try {
				BeanVerifier.verifyBean(beanClass);
			} catch (AssertionError | RuntimeException e) {
				throw new AssertionError("Cannot verify bean type " + beanClass.getName(), e);
			}
		}
	}

	/**
	 * Verify that bean classes in given packageName have valid bean getters/setters, equals/hashCode, and toString methods
	 */
	public static void verifyBeansIn(String packageName) {
		Class<?>[] beanClasses = ClassPathUtils.findClassesIn(packageName);
		verifyBeans(beanClasses);
	}

	/**
	 * Verify that bean classes in given packageObj have valid bean getters/setters, equals/hashCode, and toString methods
	 */
	public static void verifyBeansIn(Package packageObj) {
		verifyBeansIn(packageObj.getName());
	}

	/**
	 * Customizes bean verification settings. Example:
	 * <pre>
	 *   BeanVerifier.forClass(Company.class)
	 *       .withSettings(settings -&gt; settings.registerFactory(Employee.class, () -&gt; createEmployee())
	 *       .withSettings(settings -&gt; settings.setDefaultIterations(10))
	 * </pre>
	 */
	BeanVerifier withSettings(Consumer<VerifierSettings> verifierSettingsEditor);

	/**
	 * Customizes bean verification settings. Example:
	 * <pre>
	 *   BeanVerifier.forClass(Company.class)
	 *       .editSettings()              // start editing settings
	 *       .setDefaultIterations(12)
	 *       .addEqualsInsignificantProperty(Company::getId)
	 *       .registerFactory(Company.class, () -&gt; company)
	 *       .addIgnoredProperty(Company::getName)
	 *       .edited()                    // finish editing settings and perform verifications
	 *       .verifyGettersAndSetters();
	 * </pre>
	 */
	VerifierSettingsEditor editSettings();

	/**
	 * Checks the bean's equals and hashCode methods.
	 * 
	 * @see EqualsMethodTester
	 * @see HashCodeMethodTester
	 */
	BeanVerifier verifyEqualsAndHashCode();

	/**
	 * Checks the bean's public getter and setter methods.
	 * <p>
	 * Each property is tested by:
	 * </p>
	 * <ol>
	 * <li>generating a random test value for the specific property type</li>
	 * <li>invoking the property setter method, passing the generated test value</li>
	 * <li>invoking the property getter method and obtaining the return value</li>
	 * <li>verifying that the value obtained from the getter method matches the value passed to the setter method</li>
	 * </ol>
	 * 
	 * <p>
	 * Each property of a type is tested in turn. Each type is tested multiple times to reduce the risk of hard-coded values
	 * within a getter or setter matching the random test values generated and the test failing to detect a bug. <br>
	 * </p>
	 * 
	 * @see BeanTester
	 */
	BeanVerifier verifyGettersAndSetters();

	/**
	 * Checks that the bean overrides the default to Object::toString method.
	 * 
	 * @see ToStringMethodTester
	 */
	BeanVerifier verifyToString();

	/**
	 * Performs {@link #verifyGettersAndSetters()}, {@link #verifyEqualsAndHashCode()} and {@link #verifyToString()}
	 */
	default void verify() {
		ServiceFactory.inScope(() -> verifyGettersAndSetters()
				.verifyEqualsAndHashCode()
				.verifyToString());
	}

}