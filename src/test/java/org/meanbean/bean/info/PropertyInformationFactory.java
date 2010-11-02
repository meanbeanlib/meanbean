package org.meanbean.bean.info;

import java.lang.reflect.Method;

/**
 * Factory that creates PropertyInformation objects.
 * 
 * @author Graham Williamson
 */
public class PropertyInformationFactory {

	/**
	 * Create a new PropertyInformation object with the specified parameters. The new PropertyInformation will not have
	 * a read method nor write method.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param isReadable
	 *            <code>true</code> if the property is readable, <code>false</code> if it is not readable.
	 * @param isWritable
	 *            <code>true</code> if the property is writable, <code>false</code> if it is not writable.
	 * 
	 * @return A new PropertyInformation object.
	 */
	public static PropertyInformation create(String name, boolean isReadable, boolean isWritable) {
		return create(name, isReadable, isWritable, null, null);
	}

	/**
	 * Create a new PropertyInformation object with the specified parameters.
	 * 
	 * @param name
	 *            The name of the property.
	 * @param isReadable
	 *            <code>true</code> if the property is readable, <code>false</code> if it is not readable.
	 * @param isWritable
	 *            <code>true</code> if the property is writable, <code>false</code> if it is not writable.
	 * @param readMethod
	 *            The read method for the property, or <code>null</code> if the property has no read method.
	 * 
	 * @param writeMethod
	 *            The write method for the property, or <code>null</code> if the property has no write method.
	 * 
	 * @return A new PropertyInformation object.
	 */
	public static PropertyInformation create(String name, boolean isReadable, boolean isWritable, Method readMethod,
	        Method writeMethod) {
		PropertyInformationBean propertyInformation = new PropertyInformationBean();
		propertyInformation.setName(name);
		propertyInformation.setReadable(isReadable);
		propertyInformation.setWritable(isWritable);
		propertyInformation.setReadMethod(readMethod);
		propertyInformation.setWriteMethod(writeMethod);
		return propertyInformation;
	}
}