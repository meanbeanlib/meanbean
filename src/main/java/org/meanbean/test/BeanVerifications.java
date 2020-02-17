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

import java.util.function.Consumer;

public class BeanVerifications {

	public static BeanVerifier verifyThat(Class<?> beanClass) {
		return new BeanVerifier(beanClass);
	}

	public static void verifyBeans(Class<?>... beanClasses) {
		for (Class<?> beanClass : beanClasses) {
			try {
				BeanVerifications.verifyBean(beanClass);
			} catch (AssertionError | RuntimeException e) {
				throw new AssertionError("Cannot verify bean type " + beanClass.getName(), e);
			}
		}
	}

	public static void verifyBean(Class<?> beanClass) {
		verifyThat(beanClass).isValidJavaBean()
				.hasValidEqualsMethod()
				.hasValidHashCodeMethod()
				.hasValidToStringMethod();
	}

	public static class BeanVerifier {
		private Class<?> beanClass;
		private BeanTesterBuilder builder = BeanTesterBuilder.newBeanTesterBuilder();

		private BeanVerifier(Class<?> beanClass) {
			this.beanClass = beanClass;
		}

		public BeanVerifier with(Consumer<BeanTesterBuilder> builderCustomizer) {
			builderCustomizer.accept(builder);
			return this;
		}

		public BeanVerifier isValidJavaBean() {
			builder.build().testBean(beanClass);
			return this;
		}

		public BeanVerifier hasValidEqualsMethod() {
			builder.buildEqualsMethodTester().testEqualsMethod(beanClass);
			return this;
		}

		public BeanVerifier hasValidHashCodeMethod() {
			builder.buildHashCodeMethodTester().testHashCodeMethod(beanClass);
			return this;
		}

		public BeanVerifier hasValidToStringMethod() {
			builder.buildToStringMethodTester().testToStringMethod(beanClass);
			return this;
		}
	}

	private BeanVerifications() {

	}
}