package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates empty NullAcceptingBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NullAcceptingBeanFactory implements EquivalentFactory<NullAcceptingBean> {

	public NullAcceptingBean create() {
		return new NullAcceptingBean();
	}
}