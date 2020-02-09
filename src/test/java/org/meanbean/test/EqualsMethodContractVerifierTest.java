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
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.CounterDrivenEqualsBeanFactory;
import org.meanbean.test.beans.DifferentTypeAcceptingBeanFactory;
import org.meanbean.test.beans.FieldDrivenEqualsBean;
import org.meanbean.test.beans.FieldDrivenEqualsBeanFactory;
import org.meanbean.test.beans.NonReflexiveBeanFactory;
import org.meanbean.test.beans.NullAcceptingBeanFactory;
import org.meanbean.test.beans.NullEquivalentFactory;

public class EqualsMethodContractVerifierTest {

	private final EqualsMethodContractVerifier verifier = new EqualsMethodContractVerifier();

	// Reflexive -------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsReflexiveShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsReflexive(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsReflexiveShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsReflexive(new NullEquivalentFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsReflexiveShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		verifier.verifyEqualsReflexive(new NonReflexiveBeanFactory());
	}

	@Test
	public void verifyEqualsReflexiveShouldNotThrowAssertionErrorWhenEqualsIsReflexive() throws Exception {
		verifier.verifyEqualsReflexive(new BeanFactory());
	}

	// Symmetric -------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsSymmetricShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsSymmetric(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsSymmetricShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsSymmetric(new NullEquivalentFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsSymmetricShouldPreventTestingSymmetricOfNonEqualObjects() throws Exception {
		verifier.verifyEqualsSymmetric(new FieldDrivenEqualsBeanFactory(false));
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsSymmetricShouldThrowAssertionErrorWhenEqualsIsNotSymmetric() throws Exception {
		verifier.verifyEqualsSymmetric(new EquivalentFactory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
            public FieldDrivenEqualsBean create() {
				// 2nd object created by factory always returns false from equals(); others always return true
				return new FieldDrivenEqualsBean(counter++ != 1);
			}
		});
	}

	@Test
	public void verifyEqualsSymmetricShouldNotThrowAssertionErrorWhenEqualsIsSymmetric() throws Exception {
		verifier.verifyEqualsSymmetric(new BeanFactory());
	}

	// Transitive ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsTransitiveShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsTransitive(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsTransitiveShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsTransitive(new NullEquivalentFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsTransitiveShouldPreventTestingTransitiveOfNonEqualObjects() throws Exception {
		verifier.verifyEqualsTransitive(new FieldDrivenEqualsBeanFactory(false));
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsTransitiveShouldThrowAssertionErrorWhenEqualsIsNotTransitive() throws Exception {
		verifier.verifyEqualsTransitive(new CounterDrivenEqualsBeanFactory(2));
	}

	@Test
	public void verifyEqualsTransitiveShouldNotThrowAssertionErrorWhenEqualsIsTransitive() throws Exception {
		verifier.verifyEqualsTransitive(new BeanFactory());
	}

	// Consistent ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsConsistentShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsConsistent(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsConsistentShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsConsistent(new NullEquivalentFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsConsistentShouldThrowAssertionErrorWhenEqualsIsInconsistent() throws Exception {
		verifier.verifyEqualsConsistent(new CounterDrivenEqualsBeanFactory(97));
	}

	@Test
	public void verifyEqualsConsistentShouldNotThrowAssertionErrorWhenEqualsIsConsistent() throws Exception {
		verifier.verifyEqualsConsistent(new BeanFactory());
	}

	// Null ------------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsNullShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsNull(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsNullShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsNull(new NullEquivalentFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsNullShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		verifier.verifyEqualsNull(new NullAcceptingBeanFactory());
	}

	@Test
	public void verifyEqualsNullShouldNotThrowAssertionErrorWhenEqualsIsFalseForNull() throws Exception {
		verifier.verifyEqualsNull(new BeanFactory());
	}

	// Different Type --------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsDifferentTypeShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsDifferentType(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsDifferentTypeShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsDifferentType(new NullEquivalentFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsDifferentTypeShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		verifier.verifyEqualsDifferentType(new DifferentTypeAcceptingBeanFactory());
	}

	@Test
	public void verifyEqualsDifferentTypeShouldNotThrowAssertionErrorWhenEqualsIsFalseForDifferentType()
	        throws Exception {
		verifier.verifyEqualsDifferentType(new BeanFactory());
	}

	// Equals ----------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsMethod(new NullEquivalentFactory());
	}

	@Test
	public void verifyEqualsShouldNotThrowAssertionErrorWhenEqualsIsCorrect() throws Exception {
		verifier.verifyEqualsMethod(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		verifier.verifyEqualsMethod(new NonReflexiveBeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenEqualsIsInconsistent() throws Exception {
		verifier.verifyEqualsMethod(new CounterDrivenEqualsBeanFactory(97));
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		verifier.verifyEqualsMethod(new NullAcceptingBeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		verifier.verifyEqualsMethod(new DifferentTypeAcceptingBeanFactory());
	}
}
