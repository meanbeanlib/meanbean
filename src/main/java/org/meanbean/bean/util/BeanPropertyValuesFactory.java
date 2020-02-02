package org.meanbean.bean.util;

import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.bean.util.PropertyInformationFilter.PropertyVisibility;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Factory that creates a <code>Map</code> of values, keyed by property name, that could be used to populate
 * the properties of a Bean, based on information about the Bean provided in the form of a <code>BeanInformation</code>
 * instance.
 * 
 * @author Graham Williamson
 */
public class BeanPropertyValuesFactory implements Factory<Map<String, Object>> {

	/** Logging mechanism. */
	private static final $Logger logger = $LoggerFactory.getLogger(BeanPropertyValuesFactory.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(logger);

	/** Information of the object to create. */
	private final BeanInformation beanInformation;

	/** A means of acquiring a suitable Factory for use when creating values. */
	private final FactoryLookupStrategy factoryLookupStrategy;

	/**
	 * Construct a new Bean Property Values Factory.
	 * 
	 * @param beanInformation
	 *            Information used to create property values for a bean.
	 * @param factoryLookupStrategy
	 *            Provides a means of acquiring a suitable Factory for use when creating values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified BeanInformation or FactoryLookupStrategy is deemed illegal. For example, if either
	 *             is <code>null</code>.
	 */
	public BeanPropertyValuesFactory(BeanInformation beanInformation, FactoryLookupStrategy factoryLookupStrategy)
	        throws IllegalArgumentException {
		validationHelper.ensureExists("beanInformation", "populate bean", beanInformation);
		validationHelper.ensureExists("factoryLookupStrategy", "populate bean", factoryLookupStrategy);
		this.beanInformation = beanInformation;
		this.factoryLookupStrategy = factoryLookupStrategy;
	}

	/**
	 * Create a Map of values that could be used to populate the properties of the Bean defined by the BeanInformation
	 * provided upon construction of this Factory, keyed by property name.
	 * 
	 * @return A <code>Map</code> of values that could be used to populate the properties of the Bean defined by the
	 *         BeanInformation, keyed by the property name.
	 * 
	 * @throws ObjectCreationException
	 *             If an error occurs when creating the map of values.
	 */
	@Override
    public Map<String, Object> create() throws ObjectCreationException {
		Map<String, Object> propertyValues = new HashMap<String, Object>();
		Collection<PropertyInformation> writableProperties =
		        PropertyInformationFilter.filter(beanInformation.getProperties(), PropertyVisibility.WRITABLE);
		for (PropertyInformation property : writableProperties) {
			String propertyName = property.getName();
			Factory<?> valueFactory;
			try {
				valueFactory =
				        factoryLookupStrategy.getFactory(beanInformation, propertyName,
				                property.getWriteMethodParameterType(), null);
				Object value = valueFactory.create();
				propertyValues.put(propertyName, value);
			} catch (Exception e) {
				String message = "Failed to create a value for property [" + propertyName + "].";
				logger.error("create:{} Throw ObjectCreationException.", message, e);
				throw new ObjectCreationException(message, e);
			}
		}
		return propertyValues;
	}
}