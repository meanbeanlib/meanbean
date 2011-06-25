package org.meanbean.bean.util;

import java.util.Map;

import org.meanbean.bean.info.BeanInformation;

/**
 * Defines an object that affords functionality to populate a bean (set its fields) with specified values.
 * 
 * @author Graham Williamson
 */
public interface BeanPopulator {

	/**
	 * Populate the specified bean with the specified values. Values are keyed by property name (e.g. "firstName") and
	 * are matched to properties on the bean and their setters (e.g. "setFirstName"). Only properties with a setter
	 * method and an entry in the values map will be set. Any entries in the values map that do not exist on the bean
	 * are ignored.
	 * 
	 * @param bean
	 *            The object to populate.
	 * @param beanInformation
	 *            Information about the object to populate.
	 * @param values
	 *            The values to populate the object with, keyed by property name (e.g. "firstName").
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the parameters are deemed illegal. For example, if any are null.
	 * @throws BeanPopulationException
	 *             If an error occurs when populating the object.
	 */
	public void populate(Object bean, BeanInformation beanInformation, Map<String, Object> values)
	        throws IllegalArgumentException, BeanPopulationException;
}