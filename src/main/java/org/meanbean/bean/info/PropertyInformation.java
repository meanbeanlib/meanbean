package org.meanbean.bean.info;

import java.lang.reflect.Method;

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

}