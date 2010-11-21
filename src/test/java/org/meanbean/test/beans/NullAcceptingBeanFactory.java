package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory that creates empty NullAcceptingBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NullAcceptingBeanFactory implements Factory<NullAcceptingBean> {

	@Override
	public NullAcceptingBean create() {
		return new NullAcceptingBean();
	}
}