package org.meanbean.test.beans;

/**
 * A bean whose equals method returns true when compared with null. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NullAcceptingBean extends Bean {

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return true;
		}
		return super.equals(obj);
	}
}