package org.meanbean.test.beans;

/**
 * Extension of Bean that returns an incremental hashCode value starting from 1. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class InstanceIncrementalHashCodeBean extends Bean {

	private int nextHashCode = 1;

	/**
	 * Returns an incremental hashCode value starting from 1.
	 * 
	 * @return Hash code value.
	 */
	@Override
	public int hashCode() {
		return nextHashCode++;
	}
}