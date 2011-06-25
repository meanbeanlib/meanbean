package org.meanbean.test.beans;

/**
 * Extension of Bean that returns an incremental hashCode value shared by all instances of the class, starting from 1.
 * This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class ClassIncrementalHashCodeBean extends Bean {

	private static int NEXT_HASH_CODE = 1;

	/**
	 * Returns an incremental hashCode value starting from 1.
	 * 
	 * @return Hash code value.
	 */
	@Override
	public int hashCode() {
		return NEXT_HASH_CODE++;
	}
}