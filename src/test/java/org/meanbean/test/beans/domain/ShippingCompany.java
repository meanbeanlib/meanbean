package org.meanbean.test.beans.domain;

import java.util.Collection;


public class ShippingCompany extends Company {

	private Collection<Address> depots;

	public Collection<Address> getDepots() {
		return depots;
	}

	public void setDepots(Collection<Address> depots) {
		this.depots = depots;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((depots == null) ? 0 : depots.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippingCompany other = (ShippingCompany) obj;
		if (depots == null) {
			if (other.depots != null)
				return false;
		} else if (!depots.equals(other.depots))
			return false;
		return true;
	}
}