package org.meanbean.test.beans;

/**
 * A bean that returns a predetermined value when <code>hashCode()</code> is called. This should only be used for
 * testing.
 * 
 * @author Graham Williamson
 */
public class FieldDrivenHashCodeBean extends Bean {

	private final int hashCode;

	public FieldDrivenHashCodeBean(int hashCode) {
		this.hashCode = hashCode;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}
}