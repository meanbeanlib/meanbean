package org.meanbean.bean.info;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete BeanInformation that gathers and contains information about a JavaBean by using java.beans.BeanInfo.
 * 
 * @author Graham Williamson
 */
class JavaBeanInformation implements BeanInformation {

	/** The type of object this object contains information about. */
	private final Class<?> beanClass;

	/** The mechanism used to acquire information about the type. */
	private final BeanInfo beanInfo;

	/** Information about each property of the type, keyed by property name. */
	private final Map<String, PropertyInformation> properties = new HashMap<String, PropertyInformation>();

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(JavaBeanInformation.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/**
	 * Construct a new JavaBean Information object for the specified type.
	 * 
	 * @param beanClass
	 *            The type of the JavaBean object to gather information about.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass is deemed illegal. For example, if it is null.
	 * 
	 * @throws BeanInformationException
	 *             If a problem occurred when gathering information about the specified type. This may be because the
	 *             specified type is not a valid JavaBean.
	 */
	JavaBeanInformation(Class<?> beanClass) throws IllegalArgumentException, BeanInformationException {
		log.debug("JavaBeanInformation: entering with beanClass=[" + beanClass + "].");
		validationHelper.ensureExists("beanClass", "gather JavaBean information", beanClass);
		this.beanClass = beanClass;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			log.debug("JavaBeanInformation: Failed to acquire information about beanClass [" + beanClass
			        + "]. Throw BeanInformationException.", e);
			throw new BeanInformationException("Failed to acquire information about beanClass [" + beanClass + "].", e);
		}
		initialize();
		log.debug("JavaBeanInformation: exiting.");
	}

	/**
	 * Initialize this object ready for public use. This involves acquiring information about each property of the type.
	 */
	private void initialize() {
		log.debug("initialize: entering.");
		for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
			if ("class".equals(propertyDescriptor.getName()))
				continue;
			PropertyInformation propertyInformation = new PropertyDescriptorPropertyInformation(propertyDescriptor);
			properties.put(propertyInformation.getName(), propertyInformation);
		}
		log.debug("initialize: exiting.");
	}

	/**
	 * Get the type of bean this object contains information about.
	 * 
	 * @return The type of bean this object contains information about.
	 */
	@Override
	public Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * Get the names of all properties of the bean.
	 * 
	 * @return A Collection of names of all properties of the bean.
	 */
	@Override
	public Collection<String> getPropertyNames() {
		return properties.keySet();
	}

	/**
	 * Get information about all properties of the bean.
	 * 
	 * @return A Collection of all properties of the bean.
	 */
	@Override
	public Collection<PropertyInformation> getProperties() {
		return properties.values();
	}
}