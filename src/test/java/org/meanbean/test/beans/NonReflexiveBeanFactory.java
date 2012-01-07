package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates empty NonReflexiveBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NonReflexiveBeanFactory implements EquivalentFactory<NonReflexiveBean> {

	public NonReflexiveBean create() {
		return new NonReflexiveBean();
	}
}