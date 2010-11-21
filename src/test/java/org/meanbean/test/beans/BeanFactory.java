package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory that creates logically equivalent Bean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class BeanFactory implements Factory<Bean> {

	@Override
	public Bean create() {
		Bean bean = new Bean();
		bean.setName("MY_NAME");
		return bean;
	}
}
