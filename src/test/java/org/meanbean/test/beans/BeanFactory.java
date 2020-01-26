package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates logically equivalent Bean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class BeanFactory implements EquivalentFactory<Bean> {

	public static final String NAME = "MY_NAME";

	@Override
    public Bean create() {
		Bean bean = new Bean();
		bean.setName(NAME);
		return bean;
	}
}
