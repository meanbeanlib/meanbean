package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * A factory that creates CounterDrivenEqualsBeans. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class CounterDrivenEqualsBeanFactory implements Factory<CounterDrivenEqualsBean> {

	public static final String NAME = "TEST_NAME";

	private final int falseInvocationIndex;

	public CounterDrivenEqualsBeanFactory(int falseInvocationIndex) {
		this.falseInvocationIndex = falseInvocationIndex;
	}

	@Override
	public CounterDrivenEqualsBean create() {
		CounterDrivenEqualsBean bean = new CounterDrivenEqualsBean(falseInvocationIndex);
		bean.setName(NAME);
		return bean;
	}
}