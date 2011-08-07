package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates FieldDrivenEqualsBean instances, populated with the specified equalsResult. This should only be
 * used for testing.
 * 
 * @author Graham Williamson
 */
public class FieldDrivenEqualsBeanFactory implements EquivalentFactory<FieldDrivenEqualsBean> {

	private final boolean equalsResult;

	public FieldDrivenEqualsBeanFactory(boolean equalsResult) {
		this.equalsResult = equalsResult;
	}

	@Override
	public FieldDrivenEqualsBean create() {
		FieldDrivenEqualsBean bean = new FieldDrivenEqualsBean(equalsResult);
		bean.setName("TEST_NAME");
		return bean;
	}
}