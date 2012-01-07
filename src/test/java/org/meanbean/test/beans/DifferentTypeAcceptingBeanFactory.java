package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates empty DifferentTypeAcceptingBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class DifferentTypeAcceptingBeanFactory implements EquivalentFactory<DifferentTypeAcceptingBean> {

	public DifferentTypeAcceptingBean create() {
		return new DifferentTypeAcceptingBean();
	}
}