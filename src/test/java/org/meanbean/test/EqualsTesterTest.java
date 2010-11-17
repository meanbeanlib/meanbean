package org.meanbean.test;

import org.junit.Test;
import org.meanbean.lang.Factory;

public class EqualsTesterTest {

	private final EqualsTester equalsTester = new EqualsTester();

	// Reflexive -------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsReflexiveShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsReflexive(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsReflexiveShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsReflexive(new NullFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsReflexiveShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		equalsTester.testEqualsReflexive(new Factory<NonReflexiveBean>() {
			@Override
			public NonReflexiveBean create() {
				return new NonReflexiveBean();
			}
		});
	}

	@Test
	public void testEqualsReflexiveShouldNotThrowAssertionErrorWhenEqualsIsReflexive() throws Exception {
		equalsTester.testEqualsReflexive(new BeanFactory());
	}

	private static class NonReflexiveBean extends Bean {
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return false;
			}
			return super.equals(obj);
		}
	}

	// Symmetric -------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsSymmetricShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsSymmetric(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsSymmetricShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsSymmetric(new NullFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsSymmetricShouldPreventTestingSymmetricOfNonEqualObjects() throws Exception {
		equalsTester.testEqualsSymmetric(new Factory<FieldDrivenEqualsBean>() {
			@Override
			public FieldDrivenEqualsBean create() {
				return new FieldDrivenEqualsBean(false);
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsSymmetricShouldThrowAssertionErrorWhenEqualsIsNotSymmetric() throws Exception {
		equalsTester.testEqualsSymmetric(new Factory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
			public FieldDrivenEqualsBean create() {
				// 2nd object created by factory always returns false from equals(); others always return true
				return new FieldDrivenEqualsBean(counter++ != 1);
			}
		});
	}

	@Test
	public void testEqualsSymmetricShouldNotThrowAssertionErrorWhenEqualsIsSymmetric() throws Exception {
		equalsTester.testEqualsSymmetric(new BeanFactory());
	}

	// Transitive ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsTransitiveShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsTransitive(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsTransitiveShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsTransitive(new NullFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsTransitiveShouldPreventTestingTransitiveOfNonEqualObjects() throws Exception {
		equalsTester.testEqualsTransitive(new Factory<FieldDrivenEqualsBean>() {
			@Override
			public FieldDrivenEqualsBean create() {
				return new FieldDrivenEqualsBean(false);
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsTransitiveShouldThrowAssertionErrorWhenEqualsIsNotTransitive() throws Exception {
		equalsTester.testEqualsTransitive(new CounterDrivenBeanFactory(2));
	}

	@Test
	public void testEqualsTransitiveShouldNotThrowAssertionErrorWhenEqualsIsTransitive() throws Exception {
		equalsTester.testEqualsTransitive(new BeanFactory());
	}

	private static class CounterDrivenEqualsBean extends Bean {

		private final int falseCounterThreshold;

		private int counter;

		public CounterDrivenEqualsBean(int falseCounterThreshold) {
			this.falseCounterThreshold = falseCounterThreshold;
		}

		@Override
		public boolean equals(Object obj) {
			if (counter++ == falseCounterThreshold) {
				return false;
			}
			return super.equals(obj);
		}
	}

	private static class CounterDrivenBeanFactory implements Factory<CounterDrivenEqualsBean> {

		private final int falseCounterThreshold;

		public CounterDrivenBeanFactory(int falseCounterThreshold) {
			this.falseCounterThreshold = falseCounterThreshold;
		}

		@Override
		public CounterDrivenEqualsBean create() {
			return new CounterDrivenEqualsBean(falseCounterThreshold);
		}
	}

	// Consistent ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsConsistentShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsConsistent(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsConsistentShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsConsistent(new NullFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsConsistentShouldThrowAssertionErrorWhenEqualsIsInconsistent() throws Exception {
		equalsTester.testEqualsConsistent(new CounterDrivenBeanFactory(97));
	}

	@Test
	public void testEqualsConsistentShouldNotThrowAssertionErrorWhenEqualsIsConsistent() throws Exception {
		equalsTester.testEqualsConsistent(new BeanFactory());
	}

	// Null ------------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsNullShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsNull(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsNullShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsNull(new NullFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsNullShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		equalsTester.testEqualsNull(new Factory<NullAcceptingBean>() {
			@Override
			public NullAcceptingBean create() {
				return new NullAcceptingBean();
			}
		});
	}

	@Test
	public void testEqualsNullShouldNotThrowAssertionErrorWhenEqualsIsFalseForNull() throws Exception {
		equalsTester.testEqualsNull(new BeanFactory());
	}

	private static class NullAcceptingBean extends Bean {
		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return true;
			}
			return super.equals(obj);
		}
	}

	// Different Type --------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsDifferentTypeShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsDifferentType(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsDifferentTypeShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsDifferentType(new NullFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsDifferentTypeShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		equalsTester.testEqualsDifferentType(new Factory<DifferentTypeAcceptingBean>() {
			@Override
			public DifferentTypeAcceptingBean create() {
				return new DifferentTypeAcceptingBean();
			}
		});
	}

	@Test
	public void testEqualsDifferentTypeShouldNotThrowAssertionErrorWhenEqualsIsFalseForDifferentType() throws Exception {
		equalsTester.testEqualsDifferentType(new BeanFactory());
	}

	private static class DifferentTypeAcceptingBean extends Bean {
		@Override
		public boolean equals(Object obj) {
			if ((obj != null) && (getClass() != obj.getClass())) {
				return true;
			}
			return super.equals(obj);
		}
	}

	// Equals ----------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventNullFactory() throws Exception {
		equalsTester.testEquals(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEquals(new NullFactory());
	}

	@Test
	public void testEqualsShouldNotThrowAssertionErrorWhenEqualsIsCorrect() throws Exception {
		equalsTester.testEquals(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		equalsTester.testEquals(new Factory<NonReflexiveBean>() {
			@Override
			public NonReflexiveBean create() {
				return new NonReflexiveBean();
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsShouldThrowAssertionErrorWhenEqualsIsInconsistent() throws Exception {
		equalsTester.testEquals(new CounterDrivenBeanFactory(97));
	}

	@Test(expected = AssertionError.class)
	public void testEqualsShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		equalsTester.testEquals(new Factory<NullAcceptingBean>() {
			@Override
			public NullAcceptingBean create() {
				return new NullAcceptingBean();
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		equalsTester.testEquals(new Factory<DifferentTypeAcceptingBean>() {
			@Override
			public DifferentTypeAcceptingBean create() {
				return new DifferentTypeAcceptingBean();
			}
		});
	}
}