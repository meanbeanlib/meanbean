package org.meanbean.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.test.beans.domain.EmployeeWithBrokenManagerSetter;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasicBeanTesterDomainTest {

	private BeanTester beanTester;

	@Before
	public void before() {
		beanTester = new BeanTester();
	}

	@Test
	public void testBeanShouldThrowAssertionErrorWhenEmployeeManagerSetterDoesNotChangeFieldAsExpected()
	        throws Exception {
		AssertionError assertionError = null;
		try {
			beanTester.testBean(EmployeeWithBrokenManagerSetter.class);
			fail("AssertionError should have been thrown.");
		} catch (AssertionError e) {
			assertionError = e;
		}
		assertTrue(assertionError.getMessage().startsWith("Property [manager] getter did not return test value."));
	}
}