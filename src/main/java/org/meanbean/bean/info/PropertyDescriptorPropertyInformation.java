package org.meanbean.bean.info;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Concrete implementation of PropertyInformation that provides information about a JavaBean property based on a
 * PropertyDescriptor.
 * 
 * @author Graham Williamson
 */
class PropertyDescriptorPropertyInformation implements PropertyInformation {

	/** The name of the property. */
	private final String name;

	/** The underlying/wrapped PropertyDescriptor. */
	private final PropertyDescriptor propertyDescriptor;

	/**
	 * Construct a new Property Descriptor Property Information based on the specified Property Descriptor.
	 * 
	 * @param propertyDescriptor
	 *            The PropertyDescriptor this object will wrap.
	 */
	PropertyDescriptorPropertyInformation(PropertyDescriptor propertyDescriptor) {
		name = propertyDescriptor.getName();
		this.propertyDescriptor = propertyDescriptor;
	}

	/**
	 * Get the name of the property.
	 * 
	 * @return The name of the property.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Is the property publicly readable?
	 * 
	 * That is, does the property have a public getter method?
	 * 
	 * @return <code>true</code> if the property is publicly readable; <code>false</code> otherwise.
	 */
	public boolean isReadable() {
		return getReadMethod() != null;
	}

	/**
	 * Is the property publicly writable?
	 * 
	 * That is, does the property have a public setter method?
	 * 
	 * @return <code>true</code> if the property is publicly writable; <code>false</code> otherwise.
	 */
	public boolean isWritable() {
		return getWriteMethod() != null;
	}

	/**
	 * Is the property both publicly readable and writable?
	 * 
	 * That is, does the property have both a public getter and public setter method?
	 * 
	 * @return <code>true</code> if the property is publicly readable and publicly writable; <code>false</code>
	 *         otherwise.
	 */
	public boolean isReadableWritable() {
		return isReadable() && isWritable();
	}

	/**
	 * Get the public read method of the property; its getter method.
	 * 
	 * @return The public read method of the property. If the property is not publicly readable, <code>null</code> is
	 *         returned.
	 */
	public Method getReadMethod() {
		return propertyDescriptor.getReadMethod();
	}

	/**
	 * Get the public write method of the property; its setter method.
	 * 
	 * @return The public write method of the property. If the property is not publicly writable, <code>null</code> is
	 *         returned.
	 */
	public Method getWriteMethod() {
		return propertyDescriptor.getWriteMethod();
	}

	/**
	 * Get a human-readable String representation of this object.
	 * 
	 * @return A human-readable String representation of this object.
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name)
		        .append("isReadable", isReadable()).append("readMethod", getReadMethod())
		        .append("isWritable", isWritable()).append("writeMethod", getWriteMethod())
		        .append("isReadableWritable", isReadableWritable()).toString();
	}
}