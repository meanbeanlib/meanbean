package org.meanbean.bean.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Factory that creates object instances based on provided BeanInformation.
 * 
 * @author Graham Williamson
 */
public class DynamicBeanFactory implements Factory<Object> {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(DynamicBeanFactory.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** The BeanInformation that should be used to create instances of a bean. */
	private final BeanInformation beanInformation;

	/**
	 * Construct a new Factory that will create object instances based on the specified BeanInformation.
	 * 
	 * @param beanInformation
	 *            Information used to create instances of a bean.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified BeanInformation is deemed illegal. For example, if it is <code>null</code>.
	 */
	public DynamicBeanFactory(BeanInformation beanInformation) throws IllegalArgumentException {
		validationHelper.ensureExists("beanInformation", "construct Factory", beanInformation);
		this.beanInformation = beanInformation;
	}

	/**
	 * Create a new instance of the Bean described in the provided BeanInformation.
	 * 
	 * @throws BeanCreationException
	 *             If an error occurs when creating an instance of the Bean.
	 */
	@Override
	public Object create() throws BeanCreationException {
		log.debug("create: entering.");
		try {
			Object result = beanInformation.getBeanClass().newInstance();
			log.debug("create: exiting returning [" + result + "].");
			return result;
		} catch (InstantiationException e) {
			String message = "Failed to instantiate bean of type [" + beanInformation.getBeanClass().getName()
			        + "] due to InstantiationException.";
			log.debug("create: " + message + " Throw BeanCreationException.", e);
			throw new BeanCreationException(message, e);
		} catch (IllegalAccessException e) {
			String message = "Failed to instantiate bean of type [" + beanInformation.getBeanClass().getName()
			        + "] due to IllegalAccessException.";
			log.debug("create: " + message + " Throw BeanCreationException.", e);
			throw new BeanCreationException(message, e);
		}
	}
}