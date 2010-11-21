package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory that creates empty DifferentTypeAcceptingBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class DifferentTypeAcceptingBeanFactory implements Factory<DifferentTypeAcceptingBean> {

	@Override
	public DifferentTypeAcceptingBean create() {
		return new DifferentTypeAcceptingBean();
	}
}