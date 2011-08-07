package org.meanbean.test;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.CounterDrivenHashCodeBean;
import org.meanbean.test.beans.FieldDrivenEqualsBeanFactory;
import org.meanbean.test.beans.FieldDrivenHashCodeBean;

public class HashCodeMethodTesterTest {

	private final HashCodeMethodTester tester = new HashCodeMethodTester();

	private static class NullFactory implements Factory<Object> {
		@Override
		public Object create() {
			return null;
		}
	}

	// Equal -----------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventNullFactory() throws Exception {
		tester.testHashCodesEqual(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		tester.testHashCodesEqual(new NullFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodesEqualShouldPreventTestingNonEqualObjects() throws Exception {
		tester.testHashCodesEqual(new FieldDrivenEqualsBeanFactory(false));
	}

	@Test(expected = AssertionError.class)
	public void testHashCodesEqualShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		tester.testHashCodesEqual(new Factory<FieldDrivenHashCodeBean>() {
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

	// Consistent ------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeConsistentShouldPreventNullFactory() throws Exception {
		tester.testHashCodeConsistent(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeConsistentShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		tester.testHashCodeConsistent(new NullFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeConsistentShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		tester.testHashCodeConsistent(new Factory<CounterDrivenHashCodeBean>() {
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

	// HashCode --------------------------------------------------------------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventNullFactory() throws Exception {
		tester.testHashCodeMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		tester.testHashCodeMethod(new NullFactory());
	}

	@Test
	public void testHashCodeMethodShouldNotThrowAssertionErrorWhenHashCodeIsCorrect() throws Exception {
		tester.testHashCodeMethod(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		tester.testHashCodeMethod(new Factory<CounterDrivenHashCodeBean>() {
			@Override
			public CounterDrivenHashCodeBean create() {
				return new CounterDrivenHashCodeBean();
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		tester.testHashCodeMethod(new Factory<FieldDrivenHashCodeBean>() {
			private int counter;

			@Override
			public FieldDrivenHashCodeBean create() {
				return new FieldDrivenHashCodeBean(counter++);
			}
		});
	}
}