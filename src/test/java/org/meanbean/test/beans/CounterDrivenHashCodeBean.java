package org.meanbean.test.beans;


/**
 * A bean that returns the value of an incrementing counter when <code>hashCode()</code> is called. This should only be
 * used for testing.
 * 
 * @author Graham Williamson
 */
public class CounterDrivenHashCodeBean extends Bean {

	private int counter;

	@Override
	public int hashCode() {
		return counter++;
	}
}