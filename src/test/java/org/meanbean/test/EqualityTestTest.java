package org.meanbean.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;

public class EqualityTestTest {

	private static final BeanFactory beanFactory = new BeanFactory();

	@Test
	public void absoluteShouldReturnTrueForSameInstance() throws Exception {
		Bean x = beanFactory.create();
		assertThat("Incorrect EqualityTest result.", EqualityTest.ABSOLUTE.test(x, x), is(true));
	}

	@Test
	public void absoluteShouldReturnFalseForDifferentInstances() throws Exception {
		Bean x = beanFactory.create();
		Bean y = beanFactory.create();
		assertThat("Incorrect EqualityTest result.", EqualityTest.ABSOLUTE.test(x, y), is(false));
	}

	@Test
	public void logicalShouldReturnTrueForLogicallyEquivalentObjects() throws Exception {
		Bean x = beanFactory.create();
		Bean y = beanFactory.create();
		assertThat("Incorrect EqualityTest result.", EqualityTest.LOGICAL.test(x, y), is(true));
	}

	@Test
	public void logicalShouldReturnFalseForNonLogicallyEquivalentObjects() throws Exception {
		Bean x = beanFactory.create();
		Bean y = beanFactory.create();
		y.setName(y.getName() + "_DIFFERENT");
		assertThat("Incorrect EqualityTest result.", EqualityTest.LOGICAL.test(x, y), is(false));
	}
}