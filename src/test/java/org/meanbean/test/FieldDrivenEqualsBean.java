package org.meanbean.test;

/**
 * Class that returns a predetermined value when <code>equals()</code> is called. <br/>
 * 
 * This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class FieldDrivenEqualsBean extends Bean {

	private final boolean equalsResult;

	public FieldDrivenEqualsBean(boolean equalsResult) {
		this.equalsResult = equalsResult;
	}

	@Override
	public boolean equals(Object obj) {
		return equalsResult;
	}
}
