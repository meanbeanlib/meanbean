package org.meanbean.test.beans;

/**
 * A simple single property bean with correctly implemented equals and hashCode methods, but with a getter that throws a
 * RuntimeException. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class BeanWithBadGetterMethod extends Bean {

	@Override
	public String getName() {
		throw new RuntimeException("BAD SETTER METHOD");
	}
}