package org.meanbean.test.beans;

/**
 * A bean with a simple property and a non-Bean property, meaning that test data cannot be dynamically created for the
 * non-Bean property. The equals and hashCode methods have been implemented correctly. This class should only be used
 * for testing.
 * 
 * @author Graham Williamson
 */
public class BeanWithNonBeanProperty extends Bean {

	private NonBean nonBean;

	public NonBean getNonBean() {
		return nonBean;
	}

	public void setNonBean(NonBean nonBean) {
		this.nonBean = nonBean;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompositeBean [nonBean=").append(getNonBean()).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nonBean == null) ? 0 : nonBean.hashCode());
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
		BeanWithNonBeanProperty other = (BeanWithNonBeanProperty) obj;
		if (nonBean == null) {
			if (other.nonBean != null)
				return false;
		} else if (!nonBean.equals(other.nonBean))
			return false;
		return true;
	}
}