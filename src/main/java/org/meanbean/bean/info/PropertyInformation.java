package org.meanbean.bean.info;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Defines an object that provides information about a JavaBean property.
 * 
 * @author Graham Williamson
 */
public interface PropertyInformation {

	/**
	 * Get the name of the property.
	 * 
	 * @return The name of the property.
	 */
	String getName();

	/**
	 * Is the property publicly readable?
	 * 
	 * That is, does the property have a public getter method?
	 * 
	 * @return <code>true</code> if the property is publicly readable; <code>false</code> otherwise.
	 */
	boolean isReadable();

	/**
	 * Is the property publicly writable?
	 * 
	 * That is, does the property have a public setter method?
	 * 
	 * @return <code>true</code> if the property is publicly writable; <code>false</code> otherwise.
	 */
	boolean isWritable();

	/**
	 * Is the property both publicly readable and writable?
	 * 
	 * That is, does the property have both a public getter and public setter method?
	 * 
	 * @return <code>true</code> if the property is publicly readable and publicly writable; <code>false</code>
	 *         otherwise.
	 */
	boolean isReadableWritable();

	/**
	 * Get the public read method of the property; its getter method.
	 * 
	 * @return The public read method of the property. If the property is not publicly readable, <code>null</code> is
	 *         returned.
	 */
	Method getReadMethod();

	/**
	 * Get the public write method of the property; its setter method.
	 * 
	 * @return The public write method of the property. If the property is not publicly writable, <code>null</code> is
	 *         returned.
	 */
	Method getWriteMethod();

	/**
	 * Get the return type of the read method (getter method) of the property.
	 * 
	 * @return The return type of the read method. If the property does not have a read method, returns
	 *         <code>null</code>
	 */
	Type getReadMethodReturnType();

	/**
	 * Get the parameter type of the write method (setter method) of the property.
	 * 
	 * @return The type of the write method parameter. If the property does not have a write method, returns
	 *         <code>null</code>
	 * 
	 * @throws IllegalArgumentException
	 *             If the write method takes more than one parameter, or zero parameters.
	 */
	Type getWriteMethodParameterType() throws IllegalArgumentException;
}