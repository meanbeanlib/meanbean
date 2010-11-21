package org.meanbean.test.beans;

/**
 * A single property object that does not have a no-arg constructor and therefore is not a JavaBean. The equals and
 * hashCode methods are implemented correctly. This class should be used for testing only.
 * 
 * @author Graham Williamson
 */
public class NonBean {

	private final String value;

	public NonBean(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		NonBean other = (NonBean) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}