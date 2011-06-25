package org.meanbean.test.beans;

/**
 * A bean with multiple properties, but with a broken hashCode and equals method that neglect to evaluate all
 * properties. This class should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class BrokenEqualsMultiPropertyBean extends MultiPropertyBean {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getFirstName() == null) ? 0 : getFirstName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiPropertyBean other = (MultiPropertyBean) obj;
		if (getFirstName() == null) {
			if (other.getFirstName() != null)
				return false;
		} else if (!getFirstName().equals(other.getFirstName()))
			return false;
		return true;
	}

}