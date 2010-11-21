package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory that creates FieldDrivenEqualsBean instances, populated with the specified equalsResult. This should only be
 * used for testing.
 * 
 * @author Graham Williamson
 */
public class FieldDrivenEqualsBeanFactory implements Factory<FieldDrivenEqualsBean> {

	private final boolean equalsResult;

	public FieldDrivenEqualsBeanFactory(boolean equalsResult) {
		this.equalsResult = equalsResult;
	}

	@Override
	public FieldDrivenEqualsBean create() {
		return new FieldDrivenEqualsBean(equalsResult);
	}
}