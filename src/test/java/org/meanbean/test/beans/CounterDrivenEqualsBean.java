package org.meanbean.test.beans;


/**
 * A bean whose equals method is driven by a counter that returns false on a specified invocation number. This class
 * should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class CounterDrivenEqualsBean extends Bean {

	private final int falseInvocationIndex;

	private int counter;

	public CounterDrivenEqualsBean(int falseInvocationIndex) {
		this.falseInvocationIndex = falseInvocationIndex;
	}

	@Override
	public boolean equals(Object obj) {
		if (counter++ == falseInvocationIndex) {
			return false;
		}
		return super.equals(obj);
	}
}