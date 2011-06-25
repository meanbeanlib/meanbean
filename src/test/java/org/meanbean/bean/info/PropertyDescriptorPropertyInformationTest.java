package org.meanbean.bean.info;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class PropertyDescriptorPropertyInformationTest {

	private static final String PROPERTY_NAME = "firstName";

	static class Bean {

		private String firstName;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public void getNothing() {
			// Do nothing
		}

		public void setNoNames() {
			// Do nothing
		}

		public void setFirstNameAndLastName(String firstName, String lastName) {
			setFirstName(firstName);
		}
	}

	private Method readMethod;

	private Method writeMethod;

	private Method readMethodWithVoidReturnType;

	private Method writeMethodWithZeroParameters;

	private Method writeMethodWithTwoParameters;

	private PropertyDescriptor propertyDescriptor;

	@Before
	public void before() throws Exception {
		readMethod = Bean.class.getMethod("getFirstName");
		writeMethod = Bean.class.getMethod("setFirstName", String.class);
		readMethodWithVoidReturnType = Bean.class.getMethod("getNothing");
		writeMethodWithZeroParameters = Bean.class.getMethod("setNoNames");
		writeMethodWithTwoParameters = Bean.class.getMethod("setFirstNameAndLastName", String.class, String.class);
		propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, readMethod, writeMethod);
	}

	@Test
	public void constructorShouldSetName() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property name incorrect.", propertyInformation.getName(), is(PROPERTY_NAME));
	}

	@Test
	public void constructorShouldSetReadMethod() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property read method is incorrect.", propertyInformation.getReadMethod(), is(readMethod));
	}

	@Test
	public void constructorShouldSetReadMethodEvenWhenNull() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, null, writeMethod);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property read method is incorrect.", propertyInformation.getReadMethod(), is(nullValue()));
	}

	@Test
	public void isReadableShouldReturnTrueWhenReadMethodExists() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property readability is incorrect.", propertyInformation.isReadable(), is(true));
	}

	@Test
	public void isReadableShouldReturnFalseWhenReadMethodDoesNotExist() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, null, writeMethod);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property readability is incorrect.", propertyInformation.isReadable(), is(false));
	}

	@Test
	public void constructorShouldSetWriteMethod() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property write method is incorrect.", propertyInformation.getWriteMethod(), is(writeMethod));
	}

	@Test
	public void constructorShouldSetWriteMethodEvenWhenNull() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, readMethod, null);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property write method is incorrect.", propertyInformation.getWriteMethod(), is(nullValue()));
	}

	@Test
	public void isWritableShouldReturnTrueWhenWriteMethodExists() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property writability is incorrect.", propertyInformation.isWritable(), is(true));
	}

	@Test
	public void isWritableShouldReturnFalseWhenWriteMethodDoesNotExist() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, readMethod, null);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property writability is incorrect.", propertyInformation.isWritable(), is(false));
	}

	@Test
	public void isReadableWritableShouldReturnTrueWhenReadAndWriteMethodsExist() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property readability/writability is incorrect.", propertyInformation.isReadableWritable(), is(true));
	}

	@Test
	public void isReadableWritableShouldReturnFalseWhenOnlyReadMethodExists() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, readMethod, null);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property readability/writability is incorrect.", propertyInformation.isReadableWritable(),
		        is(false));
	}

	@Test
	public void isReadableWritableShouldReturnFalseWhenOnlyWriteMethodExists() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, null, writeMethod);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property readability/writability is incorrect.", propertyInformation.isReadableWritable(),
		        is(false));
	}

	@Test
	public void getReadMethodReturnTypeShouldReturnNullWhenNoReadMethodExists() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, null, writeMethod);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property read method return type is incorrect.", propertyInformation.getReadMethodReturnType(),
		        is(nullValue()));
	}

	@Test
	public void getReadMethodReturnTypeShouldReturnCorrectReadMethodReturnType() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, readMethod, null);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertEquals("Property read method return type is incorrect.", propertyInformation.getReadMethodReturnType(),
		        String.class);
	}

	@Test
	public void getWriteMethodParameterTypeShouldReturnNullWhenNoWriteMethodExists() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, readMethod, null);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertThat("Property write method parameter type is incorrect.",
		        propertyInformation.getWriteMethodParameterType(), is(nullValue()));
	}

	@Test
	public void getWriteMethodParameterTypeShouldReturnCorrectWriteMethodParameterType() throws Exception {
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor(PROPERTY_NAME, null, writeMethod);
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		assertEquals("Property write method parameter type is incorrect.",
		        propertyInformation.getWriteMethodParameterType(), String.class);
	}

	@Test(expected = IntrospectionException.class)
	public void propertyDescriptorConstructorShouldPreventWriteMethodWithZeroParameters() throws Exception {
		PropertyDescriptor propertyDescriptor =
		        new PropertyDescriptor(PROPERTY_NAME, readMethod, writeMethodWithZeroParameters);
		new PropertyDescriptorPropertyInformation(propertyDescriptor);
	}

	@Test(expected = IntrospectionException.class)
	public void propertyDescriptorConstructorShouldPreventWriteMethodWithMoreThanOneParameter() throws Exception {
		PropertyDescriptor propertyDescriptor =
		        new PropertyDescriptor(PROPERTY_NAME, readMethod, writeMethodWithTwoParameters);
		new PropertyDescriptorPropertyInformation(propertyDescriptor);
	}

	@Test(expected = IntrospectionException.class)
	public void propertyDescriptorConstructorShouldPreventReadMethodWithVoidReturnType() throws Exception {
		PropertyDescriptor propertyDescriptor =
		        new PropertyDescriptor(PROPERTY_NAME, readMethodWithVoidReturnType, writeMethod);
		new PropertyDescriptorPropertyInformation(propertyDescriptor);
	}

	@Test
	public void testToString() throws Exception {
		PropertyDescriptorPropertyInformation propertyInformation =
		        new PropertyDescriptorPropertyInformation(propertyDescriptor);
		StringBuilder expectedStringBuilder = new StringBuilder();
		expectedStringBuilder.append("PropertyDescriptorPropertyInformation[");
		expectedStringBuilder.append("name=").append(propertyInformation.getName()).append(",");
		expectedStringBuilder.append("isReadable=").append(propertyInformation.isReadable()).append(",");
		expectedStringBuilder.append("readMethod=").append(propertyInformation.getReadMethod()).append(",");
		expectedStringBuilder.append("isWritable=").append(propertyInformation.isWritable()).append(",");
		expectedStringBuilder.append("writeMethod=").append(propertyInformation.getWriteMethod()).append(",");
		expectedStringBuilder.append("isReadableWritable=").append(propertyInformation.isReadableWritable())
		        .append("]");
		assertThat("toString is incorrect.", propertyInformation.toString(), is(expectedStringBuilder.toString()));
	}
}