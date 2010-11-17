package org.meanbean.test;

import org.meanbean.lang.Factory;

/**
 * Factory that always returns <code>null</code>. <br/>
 * 
 * This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NullFactory implements Factory<Object> {

	@Override
	public Object create() {
		return null;
	}
}