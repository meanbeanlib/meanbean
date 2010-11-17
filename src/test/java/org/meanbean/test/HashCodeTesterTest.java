package org.meanbean.test;

import org.junit.Test;
import org.meanbean.lang.Factory;

public class HashCodeTesterTest {

	private final HashCodeTester hashCodeTester = new HashCodeTester();

	private static class NullFactory implements Factory<Object> {
		@Override
		public Object create() {
			return null;
		}
	}

	// Equal -----------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventNullFactory() throws Exception {
		hashCodeTester.testHashCodesEqual(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		hashCodeTester.testHashCodesEqual(new NullFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventTestingNonEqualObjects() throws Exception {
		hashCodeTester.testHashCodesEqual(new Factory<FieldDrivenEqualsBean>() {
			@Override
			public FieldDrivenEqualsBean create() {
				return new FieldDrivenEqualsBean(false);
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testHashCodesEqualShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		hashCodeTester.testHashCodesEqual(new Factory<FieldDrivenHashCodeBean>() {
			private int counter;

			@Override
			public FieldDrivenHashCodeBean create() {
				return new FieldDrivenHashCodeBean(counter++);
			}
		});
	}

	@Test
	public void testHashCodesEqualShouldNotThrowAssertionErrorWhenHashCodesAreEqual() throws Exception {
		hashCodeTester.testHashCodesEqual(new BeanFactory());
	}

	// Consistent ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeConsistentShouldPreventNullFactory() throws Exception {
		hashCodeTester.testHashCodeConsistent(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeConsistentShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		hashCodeTester.testHashCodeConsistent(new NullFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeConsistentShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		hashCodeTester.testHashCodeConsistent(new Factory<CounterDrivenHashCodeBean>() {
			@Override
			public CounterDrivenHashCodeBean create() {
				return new CounterDrivenHashCodeBean();
			}
		});
	}

	@Test
	public void testHashCodeConsistentShouldNotThrowAssertionErrorWhenHashCodeIsConsistent() throws Exception {
		hashCodeTester.testHashCodeConsistent(new BeanFactory());
	}

	// HashCode --------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeShouldPreventNullFactory() throws Exception {
		hashCodeTester.testHashCode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		hashCodeTester.testHashCode(new NullFactory());
	}

	@Test
	public void testHashCodeShouldNotThrowAssertionErrorWhenHashCodeIsCorrect() throws Exception {
		hashCodeTester.testHashCode(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		hashCodeTester.testHashCode(new Factory<CounterDrivenHashCodeBean>() {
			@Override
			public CounterDrivenHashCodeBean create() {
				return new CounterDrivenHashCodeBean();
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		hashCodeTester.testHashCode(new Factory<FieldDrivenHashCodeBean>() {
			private int counter;

			@Override
			public FieldDrivenHashCodeBean create() {
				return new FieldDrivenHashCodeBean(counter++);
			}
		});
	}
}