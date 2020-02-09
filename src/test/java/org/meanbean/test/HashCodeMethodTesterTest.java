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

import org.junit.Test;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.ClassIncrementalHashCodeBean;
import org.meanbean.test.beans.CounterDrivenHashCodeBean;
import org.meanbean.test.beans.FieldDrivenEqualsBeanFactory;
import org.meanbean.test.beans.FieldDrivenHashCodeBean;
import org.meanbean.test.beans.InstanceIncrementalHashCodeBean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NonEqualBean;
import org.meanbean.test.beans.NullEquivalentFactory;

public class HashCodeMethodTesterTest {

	private final HashCodeMethodTester tester = new HashCodeMethodTester();

	// Equal -----------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventNullFactory() throws Exception {
		tester.testHashCodesEqual(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventNullClass() throws Exception {
		tester.testHashCodeMethod((Class<?>) null);
	}

	@Test(expected = ObjectCreationException.class)
	public void testHashCodeMethodWithNonBeanClassWillThrowObjectCreationException() throws Exception {
		tester.testHashCodeMethod(NonBean.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventTestingNonEqualObjects() throws Exception {
		tester.testHashCodeMethod(NonEqualBean.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		tester.testHashCodesEqual(new NullEquivalentFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventTestingNonEqualObjects() throws Exception {
		tester.testHashCodesEqual(new FieldDrivenEqualsBeanFactory(false));
	}

	@Test(expected = AssertionError.class)
	public void testHashCodesEqualShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		tester.testHashCodesEqual(new EquivalentFactory<FieldDrivenHashCodeBean>() {
			private int counter;

			@Override
            public FieldDrivenHashCodeBean create() {
				return new FieldDrivenHashCodeBean(counter++);
			}
		});
	}

	@Test
	public void testHashCodesEqualShouldNotThrowAssertionErrorWhenHashCodesAreEqual() throws Exception {
		tester.testHashCodesEqual(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodByClassShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		tester.testHashCodeMethod(ClassIncrementalHashCodeBean.class);
	}

	// Consistent ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeConsistentShouldPreventNullFactory() throws Exception {
		tester.testHashCodeConsistent(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeConsistentShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		tester.testHashCodeConsistent(new NullEquivalentFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeConsistentShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		tester.testHashCodeConsistent(new EquivalentFactory<CounterDrivenHashCodeBean>() {
			@Override
            public CounterDrivenHashCodeBean create() {
				return new CounterDrivenHashCodeBean();
			}
		});
	}

	@Test
	public void testHashCodeConsistentShouldNotThrowAssertionErrorWhenHashCodeIsConsistent() throws Exception {
		tester.testHashCodeConsistent(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodByClassShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		tester.testHashCodeMethod(InstanceIncrementalHashCodeBean.class);
	}

	// HashCode --------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventNullFactory() throws Exception {
		tester.testHashCodeMethod((EquivalentFactory<?>) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		tester.testHashCodeMethod(new NullEquivalentFactory());
	}

	@Test
	public void testHashCodeMethodShouldNotThrowAssertionErrorWhenHashCodeIsCorrect() throws Exception {
		tester.testHashCodeMethod(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		tester.testHashCodeMethod(new EquivalentFactory<CounterDrivenHashCodeBean>() {
			@Override
            public CounterDrivenHashCodeBean create() {
				return new CounterDrivenHashCodeBean();
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		tester.testHashCodeMethod(new EquivalentFactory<FieldDrivenHashCodeBean>() {
			private int counter;

			@Override
            public FieldDrivenHashCodeBean create() {
				return new FieldDrivenHashCodeBean(counter++);
			}
		});
	}

	@Test
	public void testHashCodeMethodByClassShouldNotThrowAssertionErrorWhenHashCodeIsCorrect() throws Exception {
		tester.testHashCodeMethod(Bean.class);
	}
}
