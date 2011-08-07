package org.meanbean.bean.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.factories.util.BasicFactoryLookupStrategy;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.beans.ComplexBean;
import org.meanbean.test.beans.NonBean;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeanPropertyValuesFactoryTest {

	@Mock
	private BeanInformation beanInformationMock;

	@Mock
	private FactoryLookupStrategy factoryLookupStrategyMock;

	private final BeanTester beanTesterReal = new BeanTester();

	private final FactoryLookupStrategy factoryLookupStrategyReal = new BasicFactoryLookupStrategy(
	        beanTesterReal.getFactoryCollection(), beanTesterReal.getRandomValueGenerator());

	private final BeanInformationFactory beanInformationFactoryReal = new JavaBeanInformationFactory();

	private final BeanInformation complexBeanInformationReal = beanInformationFactoryReal.create(ComplexBean.class);

	@Mock
	private Factory<Long> longFactoryMock;

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullBeanInformation() throws Exception {
		new BeanPropertyValuesFactory(null, factoryLookupStrategyMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullFactoryLookupStrategy() throws Exception {
		new BeanPropertyValuesFactory(beanInformationMock, null);
	}

	@Test
	public void constructorShouldPermitNonNullParameters() throws Exception {
		new BeanPropertyValuesFactory(beanInformationMock, factoryLookupStrategyMock);
	}

	@Test
	public void createShouldReturnMapContainingValuesForWritableProperties() throws Exception {
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(complexBeanInformationReal, factoryLookupStrategyReal);
		Map<String, Object> values = beanPropertyValuesFactory.create();
		assertTrue(values.containsKey("id"));
		assertTrue(values.containsKey("firstName"));
		assertTrue(values.containsKey("lastName"));
		assertTrue(values.containsKey("favouriteNumber"));
		assertTrue(values.containsKey("dateOfBirth"));
		assertFalse(values.containsKey("asString"));
		assertThat(values.get("id"), is(not(nullValue())));
		assertThat(values.get("firstName"), is(not(nullValue())));
		assertThat(values.get("lastName"), is(not(nullValue())));
		assertThat(values.get("favouriteNumber"), is(not(nullValue())));
		assertThat(values.get("dateOfBirth"), is(not(nullValue())));
		assertThat(values.size(), is(5));
	}

	@Test
	public void createShouldReturnEmptyMapWhenObjectHasNoWritableProperties() throws Exception {
		NonBean bean = new NonBean("TEST");
		BeanInformation beanInformation = beanInformationFactoryReal.create(bean.getClass());
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(beanInformation, factoryLookupStrategyReal);
		Map<String, Object> values = beanPropertyValuesFactory.create();
		assertTrue(values.isEmpty());
	}

	@Test
	public void createShouldReturnNewMapEachInvocation() throws Exception {
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(complexBeanInformationReal, factoryLookupStrategyReal);
		assertThat("Should be different instances.", beanPropertyValuesFactory.create(),
		        is(not(sameInstance(beanPropertyValuesFactory.create()))));
	}

	@Test(expected = ObjectCreationException.class)
	public void createShouldWrapExceptionsInObjectCreationException() throws Exception {
		doThrow(new RuntimeException("TEST EXCEPTION")).when(longFactoryMock).create();
		beanTesterReal.getFactoryCollection().addFactory(long.class, longFactoryMock);
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(complexBeanInformationReal, factoryLookupStrategyReal);
		beanPropertyValuesFactory.create();
	}
}