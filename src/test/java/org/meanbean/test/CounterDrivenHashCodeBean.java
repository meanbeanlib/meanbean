package org.meanbean.test;

/**
 * Class that returns the value of an incrementing counter when <code>hashCode()</code> is called. <br/>
 * 
 * This should only be used for testing.
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