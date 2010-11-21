package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory that creates empty NonReflexiveBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NonReflexiveBeanFactory implements Factory<NonReflexiveBean> {

	@Override
	public NonReflexiveBean create() {
		return new NonReflexiveBean();
	}
}