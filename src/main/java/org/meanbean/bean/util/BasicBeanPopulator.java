package org.meanbean.bean.util;

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;
import org.meanbean.util.ValidationHelper;

import java.util.Collection;
import java.util.Map;

/**
 * Affords functionality to populate a bean (set its fields) with specified values.
 * 
 * @author Graham Williamson
 */
public class BasicBeanPopulator implements BeanPopulator {

	/** Logging mechanism. */
	private static final $Logger logger = $LoggerFactory.getLogger(BasicBeanPopulator.class);

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
	@Override
    public void populate(Object bean, BeanInformation beanInformation, Map<String, Object> values)
	        throws IllegalArgumentException, BeanPopulationException {
		ValidationHelper.ensureExists("bean", "populate bean", bean);
		ValidationHelper.ensureExists("beanInformation", "populate bean", beanInformation);
		ValidationHelper.ensureExists("values", "populate bean", values);
		Collection<PropertyInformation> writableProperties =
		        PropertyInformationFilter.filter(beanInformation.getProperties(), PropertyVisibility.WRITABLE);
		for (PropertyInformation property : writableProperties) {
			String propertyName = property.getName();
			if (values.containsKey(propertyName)) {
				try {
					property.getWriteMethod().invoke(bean, values.get(propertyName));
				} catch (Exception e) {
					String message =
					        "Failed to populate property [" + propertyName + "] due to Exception ["
					                + e.getClass().getName() + "]: [" + e.getMessage() + "].";
					logger.error("populate: {} Throw BeanTestException.", message, e);
					throw new BeanPopulationException(message, e);
				}
			}
		}
	}
}