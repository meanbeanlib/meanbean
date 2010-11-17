package org.meanbean.bean.info;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class JavaBeanInformationTest {

	static class BeanWithNoProperties {
		// Nothing
	}

	static class BeanWithProperties {

		private static long nextId = 1;

		private final long id; // readable

		private String firstName; // readable-writable

		private String lastName; // readable-writable

		private Date dateOfBirth; // writable

		public BeanWithProperties() {
			id = nextId++;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public long getId() {
			return id;
		}

		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public int getAge() {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			calendar.setTime(dateOfBirth);
			int birthYear = calendar.get(Calendar.YEAR);
			return currentYear - birthYear;
		}
	}

	private static final List<String> EXPECTED_PROPERTIES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("id");
			add("firstName");
			add("lastName");
			add("dateOfBirth");
			add("age");
			Collections.sort(this);
		}
	};

	@Test(expected = IllegalArgumentException.class)
	public void shouldPreventIllegalBeanClass() throws Exception {
		new JavaBeanInformation(null);
	}

	@Test
	public void shouldHaveSameBeanClassAsClassPassedToConstructor() throws Exception {
		Class<BeanWithProperties> beanClass = BeanWithProperties.class;
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(beanClass);
		assertEquals("Incorrect beanClass.", javaBeanInformation.getBeanClass(), beanClass);
	}

	@Test
	public void shouldHavePropertyNamesOfClassPassedToConstructor() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		assertThat("Incorrect number of propertyNames.", javaBeanInformation.getPropertyNames().size(),
		        is(EXPECTED_PROPERTIES.size()));
		assertThat("Incorrect propertyNames.", EXPECTED_PROPERTIES.containsAll(javaBeanInformation.getPropertyNames()),
		        is(true));
	}

	@Test
	public void shouldHaveNoPropertyNamesWhenConstructorPassedBeanWithNoProperties() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithNoProperties.class);
		assertThat("Incorrect propertyNames.", javaBeanInformation.getPropertyNames().isEmpty(), is(true));
	}

	@Test
	public void shouldHaveSameNumberOfPropertyInformationObjectsAsNumberOfPropertiesInClassPassedToConstructor()
	        throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		assertThat("Incorrect number of properties.", javaBeanInformation.getProperties().size(),
		        is(EXPECTED_PROPERTIES.size()));
	}

	@Test
	public void shouldHaveNoPropertyInformationWhenConstructorPassedBeanWithNoProperties() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithNoProperties.class);
		assertThat("Incorrect properties.", javaBeanInformation.getProperties().isEmpty(), is(true));
	}

	@Test
	public void shouldHaveIdPropertyInformationOfClassPassedToConstructor() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		Map<String, PropertyInformation> propertyMap = convertToMapOfPropertyNamesToPropertyInformation(javaBeanInformation);
		PropertyInformation property = propertyMap.get("id");
		assertThat("Incorrect property read method.", property.getReadMethod().getName(), is("getId"));
		assertThat("Incorrect property write method.", property.getWriteMethod(), is(nullValue()));
		assertThat("Incorrect property readability.", property.isReadable(), is(true));
		assertThat("Incorrect property writability.", property.isWritable(), is(false));
		assertThat("Incorrect property writability.", property.isReadableWritable(), is(false));
	}

	@Test
	public void shouldHaveFirstNamePropertyInformationOfClassPassedToConstructor() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		Map<String, PropertyInformation> propertyMap = convertToMapOfPropertyNamesToPropertyInformation(javaBeanInformation);
		PropertyInformation property = propertyMap.get("firstName");
		assertThat("Incorrect property read method.", property.getReadMethod().getName(), is("getFirstName"));
		assertThat("Incorrect property write method.", property.getWriteMethod().getName(), is("setFirstName"));
		assertThat("Incorrect property readability.", property.isReadable(), is(true));
		assertThat("Incorrect property writability.", property.isWritable(), is(true));
		assertThat("Incorrect property writability.", property.isReadableWritable(), is(true));
	}

	@Test
	public void shouldHaveLastNamePropertyInformationOfClassPassedToConstructor() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		Map<String, PropertyInformation> propertyMap = convertToMapOfPropertyNamesToPropertyInformation(javaBeanInformation);
		PropertyInformation property = propertyMap.get("lastName");
		assertThat("Incorrect property read method.", property.getReadMethod().getName(), is("getLastName"));
		assertThat("Incorrect property write method.", property.getWriteMethod().getName(), is("setLastName"));
		assertThat("Incorrect property readability.", property.isReadable(), is(true));
		assertThat("Incorrect property writability.", property.isWritable(), is(true));
		assertThat("Incorrect property writability.", property.isReadableWritable(), is(true));
	}

	@Test
	public void shouldHaveDateOfBirthPropertyInformationOfClassPassedToConstructor() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		Map<String, PropertyInformation> propertyMap = convertToMapOfPropertyNamesToPropertyInformation(javaBeanInformation);
		PropertyInformation property = propertyMap.get("dateOfBirth");
		assertThat("Incorrect property read method.", property.getReadMethod(), is(nullValue()));
		assertThat("Incorrect property write method.", property.getWriteMethod().getName(), is("setDateOfBirth"));
		assertThat("Incorrect property readability.", property.isReadable(), is(false));
		assertThat("Incorrect property writability.", property.isWritable(), is(true));
		assertThat("Incorrect property writability.", property.isReadableWritable(), is(false));
	}

	@Test
	public void shouldHaveAgePropertyInformationOfClassPassedToConstructor() throws Exception {
		JavaBeanInformation javaBeanInformation = new JavaBeanInformation(BeanWithProperties.class);
		Map<String, PropertyInformation> propertyMap = convertToMapOfPropertyNamesToPropertyInformation(javaBeanInformation);
		PropertyInformation property = propertyMap.get("age");
		assertThat("Incorrect property read method.", property.getReadMethod().getName(), is("getAge"));
		assertThat("Incorrect property write method.", property.getWriteMethod(), is(nullValue()));
		assertThat("Incorrect property readability.", property.isReadable(), is(true));
		assertThat("Incorrect property writability.", property.isWritable(), is(false));
		assertThat("Incorrect property writability.", property.isReadableWritable(), is(false));
	}

	private Map<String, PropertyInformation> convertToMapOfPropertyNamesToPropertyInformation(
	        JavaBeanInformation javaBeanInformation) {
		Map<String, PropertyInformation> propertyMap = new HashMap<String, PropertyInformation>();
		for (PropertyInformation property : javaBeanInformation.getProperties()) {
			propertyMap.put(property.getName(), property);
		}
		return propertyMap;
	}
}