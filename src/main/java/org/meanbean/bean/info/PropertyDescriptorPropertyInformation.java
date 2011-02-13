package org.meanbean.bean.info;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public boolean isReadableWritable() {
		return isReadable() && isWritable();
	}

	/**
	 * Get the public read method of the property; its getter method.
	 * 
	 * @return The public read method of the property. If the property is not publicly readable, <code>null</code> is
	 *         returned.
	 */
	@Override
	public Method getReadMethod() {
		return propertyDescriptor.getReadMethod();
	}

	/**
	 * Get the public write method of the property; its setter method.
	 * 
	 * @return The public write method of the property. If the property is not publicly writable, <code>null</code> is
	 *         returned.
	 */
	@Override
	public Method getWriteMethod() {
		return propertyDescriptor.getWriteMethod();
	}

	/**
	 * Get the return type of the read method (getter method) of the property.
	 * 
	 * @return The return type of the read method. If the property does not have a read method, returns
	 *         <code>null</code>
	 */
	@Override
	public Class<?> getReadMethodReturnType() {
		if (isReadable()) {
			return getReadMethod().getReturnType();
		}
		return null;
	}

	/**
	 * Get the parameter type of the write method (setter method) of the property.
	 * 
	 * @return The type of the write method parameter. If the property does not have a write method, returns
	 *         <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             If the write method takes more than one parameter, or zero parameters.
	 */
	@Override
	public Class<?> getWriteMethodParameterType() throws IllegalArgumentException {
		Class<?> parameterType = null;
		Method writeMethod = getWriteMethod();
		if (writeMethod != null) {
			Class<?>[] parameterTypes = writeMethod.getParameterTypes();
			parameterType = parameterTypes[0];
		}
		return parameterType;
	}

	/**
	 * Get a human-readable String representation of this object.
	 * 
	 * @return A human-readable String representation of this object.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("PropertyDescriptorPropertyInformation[");
		str.append("name=").append(name).append(",");
		str.append("isReadable=").append(isReadable()).append(",");
		str.append("readMethod=").append(getReadMethod()).append(",");
		str.append("isWritable=").append(isWritable()).append(",");
		str.append("writeMethod=").append(getWriteMethod()).append(",");
		str.append("isReadableWritable=").append(isReadableWritable());
		str.append("]");
		return str.toString();
	}
}