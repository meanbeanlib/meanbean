package org.meanbean.test.beans;

/**
 * A bean whose equals method returns true when compared with an object of a different type. This class should only be
 * used for testing.
 * 
 * @author Graham Williamson
 */
public class DifferentTypeAcceptingBean extends Bean {

	@Override
	public boolean equals(Object obj) {
		if ((obj != null) && (getClass() != obj.getClass())) {
			return true;
		}
		return super.equals(obj);
	}
}