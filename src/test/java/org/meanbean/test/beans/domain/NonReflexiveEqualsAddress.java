package org.meanbean.test.beans.domain;


/**
 * An Address whose equals method returns false when compared with itself. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class NonReflexiveEqualsAddress extends Address {

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return false;
		}
		return super.equals(obj);
	}
}