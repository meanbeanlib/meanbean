package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

/**
 * A factory that always returns <code>null</code>. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NullEquivalentFactory implements EquivalentFactory<Object> {

	@Override
    public Object create() {
		return null;
	}
}