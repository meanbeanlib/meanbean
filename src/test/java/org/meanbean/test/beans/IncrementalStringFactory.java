package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory that creates Strings with an increasing number in them. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class IncrementalStringFactory implements Factory<String> {

	private int counter;

	public String create() {
		return "TEST_STRING_" + counter++;
	}
}