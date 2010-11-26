package org.meanbean.test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.ComplexBean;
import org.meanbean.util.RandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasicBeanTesterTest {

	private BasicBeanTester beanTester;

	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	@Mock
	private Configuration configuration;

	@Before
	public void before() {
		beanTester = new BasicBeanTester();
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
		assertThat("Configuration should not exist already.", beanTester.hasCustomConfiguration(String.class),
		        is(false));
		beanTester.addCustomConfiguration(String.class, configuration);
		assertThat("Did not add custom configuration.", beanTester.hasCustomConfiguration(String.class), is(true));
		assertThat("Did not add custom configuration.", beanTester.getCustomConfiguration(String.class),
		        is(configuration));
	}

	@Test
	public void shouldGetRandomValueGenerator() throws Exception {
		RandomValueGenerator randomValueGenerator = beanTester.getRandomValueGenerator();
		assertThat("Failed to get RandomValueGenerator.", randomValueGenerator, is(not(nullValue())));
		randomValueGenerator.nextBoolean();
	}

	@Test
	public void shouldGetFactoryRepository() throws Exception {
		FactoryCollection factoryRepository = beanTester.getFactoryCollection();
		assertThat("Failed to get FactoryRepository.", factoryRepository, is(not(nullValue())));
		@SuppressWarnings("unchecked")
		Factory<String> stringFactory = (Factory<String>) factoryRepository.getFactory(String.class);
		String randomString = stringFactory.create();
		assertThat("Failed to get random String from FactoryRepository.", randomString, is(not(nullValue())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBeanThatTakesBeanClassShouldPreventNullBeanClass() throws Exception {
		beanTester.testBean(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBeanThatTakesBeanClassAndConfigurationShouldPreventNullBeanClass() throws Exception {
		beanTester.testBean((Class<?>) null, configuration);
	}

	@Test
	public void testBeanThatTakesBeanClassAndConfigurationShouldPermitNullConfiguration() throws Exception {
		beanTester.testBean(Bean.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBeanThatTakesBeanInformationAndConfigurationShouldPreventNullBeanInformation() throws Exception {
		beanTester.testBean((BeanInformation) null, configuration);
	}

	@Test
	public void testBeanThatTakesBeanInformationAndConfigurationShouldPermitNullConfiguration() throws Exception {
		BeanInformation beanInformation = beanInformationFactory.create(Bean.class);
		beanTester.testBean(beanInformation, null);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassShouldThrowAssertionErrorForBadGetterBean() throws Exception {
		beanTester.testBean(BeanWithBadGetterMethod.class);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassShouldThrowAssertionErrorForBadSetterBean() throws Exception {
		beanTester.testBean(BeanWithBadSetterMethod.class);
	}

	@Test
	public void testBeanThatTakesBeanClassShouldNotThrowAssertionErrorWhenGettersAndSettersFunctionCorrectly()
	        throws Exception {
		beanTester.testBean(ComplexBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassAndConfigurationShouldThrowAssertionErrorForBadGetterBean() throws Exception {
		beanTester.testBean(BeanWithBadGetterMethod.class, configuration);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassAndConfigurationShouldThrowAssertionErrorForBadSetterBean() throws Exception {
		beanTester.testBean(BeanWithBadSetterMethod.class, configuration);
	}

	@Test
	public void testBeanThatTakesBeanClassAndConfigurationShouldNotThrowAssertionErrorWhenGettersAndSettersFunctionCorrectly()
	        throws Exception {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.overrideFactory("lastName", new Factory<String>() {
			@Override
			public String create() {
				return "LastName" + System.currentTimeMillis();
			}
		});
		configurationBuilder.overrideFactory("dateOfBirth", new Factory<Date>() {
			@Override
			public Date create() {
				return new Date();
			}
		});
		beanTester.testBean(ComplexBean.class, configurationBuilder.build());
	}

	@Test
	public void testBeanThatTakesBeanClassAndConfigurationShouldIgnoreBadPropertyWhenToldTo() throws Exception {
		beanTester.testBean(BadComplexBean.class, new ConfigurationBuilder().ignoreProperty("lastName").build());
	}

	// TODO TEST COMBINATIONS WITH CONFIGURATIONS AND BEAN INFORMATIONS ETC

	public static class BeanWithBadGetterMethod extends Bean {
		@Override
		public String getName() {
			return "FIXED_TEST_STRING";
		}
	}

	public static class BeanWithBadSetterMethod extends Bean {
		@Override
		public void setName(String name) {
			// do nothing
		}
	}

	public static class BadComplexBean extends ComplexBean {
		@Override
		public String getLastName() {
			return "FIXED_TEST_STRING";
		}
	}
}