package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * A factory that always returns <code>null</code>. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NullFactory implements Factory<Object> {

	@Override
    public Object create() {
		return null;
	}
}
