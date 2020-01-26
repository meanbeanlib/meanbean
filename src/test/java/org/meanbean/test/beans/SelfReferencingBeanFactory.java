package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates logically equivalent SelfReferencingBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class SelfReferencingBeanFactory implements EquivalentFactory<SelfReferencingBean> {

	private static final SelfReferencingBean parent = new SelfReferencingBean();
	static {
		parent.setFirstName("PARENT_FIRST_NAME");
	}

	@Override
    public SelfReferencingBean create() {
		SelfReferencingBean bean = new SelfReferencingBean();
		bean.setFirstName("TEST_FIRST_NAME");
		bean.setLastName("TEST_LAST_NAME");
		bean.setParent(parent);
		return bean;
	}
}
