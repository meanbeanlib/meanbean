package org.meanbean.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.factories.beans.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BrokenEqualsMultiPropertyBean;
import org.meanbean.test.beans.ComplexBean;
import org.meanbean.test.beans.DifferentTypeAcceptingBean;
import org.meanbean.test.beans.InvocationCountingFactoryWrapper;
import org.meanbean.test.beans.MultiPropertyBean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NonReflexiveBean;
import org.meanbean.test.beans.NullAcceptingBean;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdvancedEqualsMethodTesterTest {

	private final SmartEqualsMethodTester equalsTester = new AdvancedEqualsMethodTester();

	@Mock
	private EqualsMethodTester equalsMethodTesterMock;

	@Mock
	private FactoryLookupStrategy factoryLookupStrategyMock;

	@Mock
	private Configuration configurationMock;

	@Test
	public void testEqualsMethodWithoutConfigurationShouldDelegateToEqualsMethodTester() throws Exception {
		when(equalsMethodTesterMock.getFactoryLookupStrategy()).thenReturn(factoryLookupStrategyMock);
		String[] insignificantProperties = new String[] { "property1", "property2" };
		SmartEqualsMethodTester equalsTester = new AdvancedEqualsMethodTester(equalsMethodTesterMock);
		equalsTester.testEqualsMethod(ComplexBean.class, insignificantProperties);
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<Factory> argument = ArgumentCaptor.forClass(Factory.class);
		verify(equalsMethodTesterMock).testEqualsMethod(argument.capture(), (Configuration) eq(null),
		        eq(insignificantProperties[0]), eq(insignificantProperties[1]));
		Factory<?> factoryUsed = argument.getValue();
		assertThat(factoryUsed.getClass().getName(), is(EquivalentPopulatedBeanFactory.class.getName()));
	}

	@Test
	public void testEqualsMethodWithConfigurationShouldDelegateToEqualsMethodTester() throws Exception {
		when(equalsMethodTesterMock.getFactoryLookupStrategy()).thenReturn(factoryLookupStrategyMock);
		String[] insignificantProperties = new String[] { "property1", "property2" };
		SmartEqualsMethodTester equalsTester = new AdvancedEqualsMethodTester(equalsMethodTesterMock);
		equalsTester.testEqualsMethod(ComplexBean.class, configurationMock, insignificantProperties);
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<Factory> argument = ArgumentCaptor.forClass(Factory.class);
		verify(equalsMethodTesterMock).testEqualsMethod(argument.capture(), eq(configurationMock),
		        eq(insignificantProperties[0]), eq(insignificantProperties[1]));
		Factory<?> factoryUsed = argument.getValue();
		assertThat(factoryUsed.getClass().getName(), is(EquivalentPopulatedBeanFactory.class.getName()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullClass() throws Exception {
		equalsTester.testEqualsMethod(null);
	}

	@Test(expected = ObjectCreationException.class)
	public void testEqualsMethodWithNonBeanClassWillThrowObjectCreationException() throws Exception {
		equalsTester.testEqualsMethod(NonBean.class);
	}

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Bean.class);
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		equalsTester.testEqualsMethod(NonReflexiveBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		equalsTester.testEqualsMethod(NullAcceptingBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		equalsTester.testEqualsMethod(DifferentTypeAcceptingBean.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(Bean.class, (String[]) null);
	}

	@Test
	public void testEqualsMethodShouldAcceptNoInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(Bean.class);
	}

	@Test
	public void testEqualsMethodShouldAcceptEmptyInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(Bean.class, new String[] {});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldNotHaveChangedButDid() throws Exception {
		equalsTester.testEqualsMethod(MultiPropertyBean.class, "lastName");
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() throws Exception {
		equalsTester.testEqualsMethod(BrokenEqualsMultiPropertyBean.class);
	}

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenTestPasses() throws Exception {
		equalsTester.testEqualsMethod(MultiPropertyBean.class);
	}

	@Test
	public void testEqualsMethodShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		equalsTester.testEqualsMethod(MultiPropertyBean.class, configuration, "lastName");
	}

	@Test
	public void testEqualsMethodShouldUseOverrideFactory() throws Exception {
		@SuppressWarnings("unchecked")
		Factory<String> stringFactory = (Factory<String>) equalsTester.getFactoryCollection().getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		equalsTester.testEqualsMethod(Bean.class, configuration);
		assertThat("custom factory was not used", factory.getInvocationCount(),
		        is(EqualsMethodTester.TEST_ITERATIONS_PER_TYPE));
	}

	@Test
	public void testEqualsShouldTestPropertySignificanceConfigurationIterationsTimes() throws Exception {
		@SuppressWarnings("unchecked")
		Factory<String> stringFactory = (Factory<String>) equalsTester.getFactoryCollection().getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).iterations(5).build();
		equalsTester.testEqualsMethod(Bean.class, configuration);
		assertThat("global iterations was not used", factory.getInvocationCount(), is(configuration.getIterations()));
	}

	@Test
	public void testEqualsShouldTestPropertySignificanceGlobalIterationsTimes() throws Exception {
		@SuppressWarnings("unchecked")
		Factory<String> stringFactory = (Factory<String>) equalsTester.getFactoryCollection().getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		equalsTester.testEqualsMethod(Bean.class, configuration);
		assertThat("global iterations was not used", factory.getInvocationCount(),
		        is(EqualsMethodTester.TEST_ITERATIONS_PER_TYPE));
	}

	@Test
	public void testEqualsShouldPreventUnrecognisedPropertyAndPermitRecognisedProperty() throws Exception {
		String unrecognisedPropertyName = "UNRECOGNISED_PROPERTY";
		try {
			equalsTester.testEqualsMethod(Bean.class, "name", unrecognisedPropertyName);
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName + "] do not exist on "
			                + Bean.class.getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}

	@Test
	public void testEqualsShouldPreventUnrecognisedPropertiesAndPermitRecognisedProperties() throws Exception {
		String unrecognisedPropertyName1 = "UNRECOGNISED_PROPERTY_1";
		String unrecognisedPropertyName2 = "UNRECOGNISED_PROPERTY_2";
		try {
			equalsTester.testEqualsMethod(MultiPropertyBean.class, "firstName", unrecognisedPropertyName1, "lastName",
			        unrecognisedPropertyName2);
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName1 + "," + unrecognisedPropertyName2
			                + "] do not exist on " + MultiPropertyBean.class.getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}
}