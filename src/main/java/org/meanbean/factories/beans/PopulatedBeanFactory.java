package org.meanbean.factories.beans;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.util.BasicBeanPopulator;
import org.meanbean.bean.util.BeanPopulator;
import org.meanbean.bean.util.BeanPropertyValuesFactory;
import org.meanbean.factories.BasicNewObjectInstanceFactory;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Factory that creates object instances based on provided BeanInformation, assigning each instance different values.
 * 
 * @author Graham Williamson
 */
public class PopulatedBeanFactory implements Factory<Object> {

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(PopulatedBeanFactory.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** The BeanInformation that should be used to create instances of a bean. */
	private final BeanInformation beanInformation;

	/** Creates values that can be used to populate the properties of a Bean. */
	private final BeanPropertyValuesFactory beanPropertyValuesFactory;

	/** Affords functionality to populate a bean (set its fields) with specified values. */
	private final BeanPopulator beanPopulator = new BasicBeanPopulator();

	/**
	 * Construct a new Factory that creates object instances based on provided BeanInformation, assigning each instance
	 * different field values.
	 * 
	 * @param beanInformation
	 *            Information used to create instances of a bean.
	 * @param factoryLookupStrategy
	 *            Provides a means of acquiring Factories that can be used to create values for the fields of new object
	 *            instances.
	 * @throws IllegalArgumentException
	 *             If either the specified BeanInformation or the FactoryLookupStrategy is deemed illegal. For example,
	 *             if either is <code>null</code>.
	 */
	public PopulatedBeanFactory(BeanInformation beanInformation, FactoryLookupStrategy factoryLookupStrategy)
	        throws IllegalArgumentException {
		validationHelper.ensureExists("beanInformation", "construct Factory", beanInformation);
		validationHelper.ensureExists("factoryLookupStrategy", "construct Factory", factoryLookupStrategy);
		this.beanInformation = beanInformation;
		beanPropertyValuesFactory = new BeanPropertyValuesFactory(beanInformation, factoryLookupStrategy);
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
		Map<String, Object> propertyValues = beanPropertyValuesFactory.create();
		BasicNewObjectInstanceFactory beanFactory = new BasicNewObjectInstanceFactory(beanInformation.getBeanClass());
		Object result = beanFactory.create();
		beanPopulator.populate(result, beanInformation, propertyValues);
		log.debug("create: populated [" + result + "].");
		return result;
	}
}