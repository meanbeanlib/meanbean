package org.meanbean.test.beans;

/**
 * A simple single property bean with correctly implemented equals and hashCode methods, but with a setter that throws a
 * RuntimeException. Since the setter cannot be called, the getter returns a constant value. This class should only be
 * used for testing.
 * 
 * @author Graham Williamson
 */
public class BeanWithBadSetterMethod extends Bean {

	@Override
	public void setName(String name) {
		throw new RuntimeException("BAD SETTER METHOD");
	}

	@Override
	public String getName() {
		return "TEST_VALUE";
	}
}