package org.meanbean.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.factories.beans.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.ClassIncrementalHashCodeBean;
import org.meanbean.test.beans.ComplexBean;
import org.meanbean.test.beans.InstanceIncrementalHashCodeBean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NonEqualBean;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdvancedHashCodeMethodTesterTest {

	private final SmartHashCodeMethodTester hashCodeMethodTester = new AdvancedHashCodeMethodTester();

	@Mock
	private HashCodeMethodTester hashCodeMethodTesterMock;

	@Mock
	private FactoryLookupStrategy factoryLookupStrategyMock;

	@Test
	public void testHashCodeMethodShouldDelegateToHashCodeMethodTester() throws Exception {
		when(hashCodeMethodTesterMock.getFactoryLookupStrategy()).thenReturn(factoryLookupStrategyMock);
		SmartHashCodeMethodTester hashCodeMethodTester = new AdvancedHashCodeMethodTester(hashCodeMethodTesterMock);
		hashCodeMethodTester.testHashCodeMethod(ComplexBean.class);
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<Factory> argument = ArgumentCaptor.forClass(Factory.class);
		verify(hashCodeMethodTesterMock).testHashCodeMethod(argument.capture());
		Factory<?> factoryUsed = argument.getValue();
		assertThat(factoryUsed.getClass().getName(), is(EquivalentPopulatedBeanFactory.class.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventNullClass() throws Exception {
		hashCodeMethodTester.testHashCodeMethod(null);
	}

	@Test(expected = ObjectCreationException.class)
	public void testHashCodeMethodWithNonBeanClassWillThrowObjectCreationException() throws Exception {
		hashCodeMethodTester.testHashCodeMethod(NonBean.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHashCodeMethodShouldPreventTestingNonEqualObjects() throws Exception {
		hashCodeMethodTester.testHashCodeMethod(NonEqualBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodShouldThrowAssertionErrorWhenHashCodesAreNotEqual() throws Exception {
		hashCodeMethodTester.testHashCodeMethod(ClassIncrementalHashCodeBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testHashCodeMethodShouldThrowAssertionErrorWhenHashCodeIsInconsistent() throws Exception {
		hashCodeMethodTester.testHashCodeMethod(InstanceIncrementalHashCodeBean.class);
	}

	@Test
	public void testHashCodeMethodShouldNotThrowAssertionErrorWhenHashCodeIsCorrect() throws Exception {
		hashCodeMethodTester.testHashCodeMethod(Bean.class);
	}
}