package org.meanbean.bean.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.test.beans.ComplexBean;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasicBeanPopulatorTest {

	private final BeanPopulator beanPopulator = new BasicBeanPopulator();

	@Mock
	private ComplexBean beanMock;

	private BeanInformation beanInformationReal;

	@Mock
	private PropertyInformation propertyInformationMock;

	@Mock
	private BeanInformation beanInformationMock;

	private Map<String, Object> valuesReal;

	@Mock
	private Map<String, Object> valuesMock;

	@Before
	public void setup() {
		beanInformationReal = new JavaBeanInformationFactory().create(ComplexBean.class);
		valuesReal = new HashMap<String, Object>();
		valuesReal.put("id", 1L);
		valuesReal.put("firstName", "MY_FIRST_NAME");
		valuesReal.put("dateOfBirth", new Date());
		valuesReal.put("favouriteNumber", 7L);
		valuesReal.put("asString", "SHOULD_NOT_BE_USED");
		valuesReal.put("nonExistentProperty", "SHOULD_NOT_BE_USED");
	}

	@Test(expected = IllegalArgumentException.class)
	public void populateShouldPreventNullBeanArgument() throws Exception {
		beanPopulator.populate(null, beanInformationMock, valuesMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void populateShouldPreventNullBeanInformationArgument() throws Exception {
		beanPopulator.populate(beanMock, null, valuesMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void populateShouldPreventNullValuesArgument() throws Exception {
		beanPopulator.populate(beanMock, beanInformationMock, null);
	}

	@Test
	public void populateShouldSetPropertiesToValuesInValuesMap() throws Exception {
		// Input
		ComplexBean bean = new ComplexBean();
		Map<String, Object> values = new HashMap<String, Object>(valuesReal);
		values.put("lastName", "MY_LAST_NAME");
		// System under test
		beanPopulator.populate(bean, beanInformationReal, values);
		// Post-conditions
		assertThat(bean.getFirstName(), is((String) values.get("firstName")));
		assertThat(bean.getLastName(), is((String) values.get("lastName")));
		assertThat(bean.getDateOfBirth(), is((Date) values.get("dateOfBirth")));
		assertThat(bean.getFavouriteNumber(), is((Long) values.get("favouriteNumber")));
	}

	@Test
	public void populateShouldSetOnlyThosePropertiesThatExistInValuesMapMockTest() throws Exception {
		beanPopulator.populate(beanMock, beanInformationReal, valuesReal);
		verify(beanMock).setFirstName((String) valuesReal.get("firstName"));
		verify(beanMock).setDateOfBirth((Date) valuesReal.get("dateOfBirth"));
		verify(beanMock).setFavouriteNumber((Long) valuesReal.get("favouriteNumber"));
		verify(beanMock, never()).setLastName((String) valuesReal.get("lastName"));
		verify(beanMock).setId((Long) valuesReal.get("id"));
		verifyNoMoreInteractions(beanMock);
	}

	@Test
	public void populateShouldNotSetAnyPropertiesIfThereAreNoOnlyThosePropertiesThatExistInValuesMapMockTest()
	        throws Exception {
		BeanInformation beanInformation = new JavaBeanInformationFactory().create(ComplexBean.class);
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("asString", "SHOULD_NOT_BE_USED");
		values.put("nonExistentProperty", "SHOULD_NOT_BE_USED");
		beanPopulator.populate(beanMock, beanInformation, values);
		verifyZeroInteractions(beanMock);
	}

	@Test
	public void populateShouldNotSetAnyPropertiesIfValuesMapIsEmptyMockTest() throws Exception {
		BeanInformation beanInformation = new JavaBeanInformationFactory().create(ComplexBean.class);
		beanPopulator.populate(beanMock, beanInformation, new HashMap<String, Object>());
		verifyZeroInteractions(beanMock);
	}

	@Test(expected = BeanPopulationException.class)
	public void populateShouldWrapExceptionsInBeanPopulationExceptions() throws Exception {
		Collection<PropertyInformation> properties = new ArrayList<PropertyInformation>();
		when(propertyInformationMock.isWritable()).thenReturn(true);
		when(propertyInformationMock.getName()).thenReturn("id");
		when(propertyInformationMock.getWriteMethod()).thenReturn(null);
		properties.add(propertyInformationMock);
		when(beanInformationMock.getProperties()).thenReturn(properties);
		beanPopulator.populate(beanMock, beanInformationMock, valuesReal);
	}
}