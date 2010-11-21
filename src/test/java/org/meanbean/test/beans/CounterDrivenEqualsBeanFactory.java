package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * A factory that creates CounterDrivenEqualsBeans. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class CounterDrivenEqualsBeanFactory implements Factory<CounterDrivenEqualsBean> {

	private final int falseInvocationIndex;

	public CounterDrivenEqualsBeanFactory(int falseInvocationIndex) {
		this.falseInvocationIndex = falseInvocationIndex;
	}

	@Override
	public CounterDrivenEqualsBean create() {
		return new CounterDrivenEqualsBean(falseInvocationIndex);
	}
}