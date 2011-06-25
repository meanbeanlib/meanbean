package org.meanbean.test;

import org.junit.Test;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;

public class InsignificantObjectPropertyEqualityConsistentAsserterTest {

	private final ObjectPropertyEqualityConsistentAsserter objectPropertyEqualityConsistentAsserter =
	        new InsignificantObjectPropertyEqualityConsistentAsserter();

	private final BeanFactory beanFactory = new BeanFactory();

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullPropertyName() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent(null, new Object(), new Object(), new Object(),
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullOriginalObject() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", null, new Object(), new Object(),
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullModifiedObject() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", new Object(), null, new Object(),
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullOriginalPropertyValue() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", new Object(), new Object(), null,
		        new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void assertConsistentShouldPreventNullNewPropertyValue() throws Exception {
		objectPropertyEqualityConsistentAsserter.assertConsistent("propertyName", new Object(), new Object(),
		        new Object(), null);
	}

	@Test
	public void assertConsistentShouldNotThrowAssertionErrorWhenValuesDifferAndObjectsEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		Long originalPropertyValue = 2L;
		Long newPropertyValue = 3L;
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}

	@Test(expected = AssertionError.class)
	public void assertConsistentShouldThrowAssertionErrorWhenValuesDifferAndObjectsNotEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		String originalPropertyValue = originalObject.getName();
		String newPropertyValue = modifiedObject.getName() + "_DIFFERENT";
		modifiedObject.setName(newPropertyValue);
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}

	@Test(expected = AssertionError.class)
	public void assertConsistentShouldThrowAssertionErrorWhenValuesSameButObjectsNotEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		modifiedObject.setName(modifiedObject.getName() + "_DIFFERENT");
		Long originalPropertyValue = 2L;
		Long newPropertyValue = 2L;
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}

	@Test
	public void assertConsistentShouldNotThrowAssertionErrorWhenValuesSameAndObjectsEqual() throws Exception {
		Bean originalObject = beanFactory.create();
		Bean modifiedObject = beanFactory.create();
		Long originalPropertyValue = 2L;
		Long newPropertyValue = 2L;
		objectPropertyEqualityConsistentAsserter.assertConsistent("name", originalObject, modifiedObject,
		        originalPropertyValue, newPropertyValue);
	}
}