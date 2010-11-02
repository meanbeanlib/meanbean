package org.meanbean.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeanTesterImplTest {

	@Mock
	private Configuration configuration;

	private BeanTesterImpl beanTester;
	
	@Before
	public void before() {
		beanTester = new BeanTesterImpl();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setIterationsShouldPreventIterationsLessThanOne() throws Exception {
		beanTester.setIterations(0);
	}

	@Test
	public void setIterationsShouldSetIterations() throws Exception {
		int iterations = 234;
		beanTester.setIterations(iterations);
		assertThat("Incorrect iterations.", beanTester.getIterations(), is(iterations));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addCustomConfigurationShouldPreventNullBeanClass() throws Exception {
		beanTester.addCustomConfiguration(null, configuration);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addCustomConfigurationShouldPreventNullConfiguration() throws Exception {
		beanTester.addCustomConfiguration(String.class, null);
	}

	@Test
	public void shouldAddCustomConfiguration() throws Exception {
		assertThat("Configuration should not exist already.", beanTester.hasCustomConfiguration(String.class), is(false));
		beanTester.addCustomConfiguration(String.class, configuration);
		assertThat("Did not add custom configuration.", beanTester.hasCustomConfiguration(String.class), is(true));
		assertThat("Did not add custom configuration.", beanTester.getCustomConfiguration(String.class), is(configuration));
	}

}