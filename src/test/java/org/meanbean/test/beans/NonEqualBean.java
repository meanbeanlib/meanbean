package org.meanbean.test.beans;

/**
 * Extension of Bean that never considers another Bean logically equivalent. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NonEqualBean extends Bean {

	/**
	 * Always returns false.
	 * 
	 * @param obj
	 *            Not used.
	 * 
	 * @return <code>false</code>, always.
	 */
	@Override
	public boolean equals(Object obj) {
		return false;
	}
}