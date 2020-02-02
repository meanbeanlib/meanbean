package org.meanbean.bean.info;

import org.meanbean.util.ServiceDefinition;

/**
 * Defines an object that creates BeanInformation objects.
 * 
 * @author Graham Williamson
 */
public interface BeanInformationFactory {

	public static ServiceDefinition<BeanInformationFactory> getServiceDefinition() {
		return new ServiceDefinition<>(BeanInformationFactory.class);
	}

	public static BeanInformationFactory getInstance() {
		return getServiceDefinition().getServiceFactory()
				.getFirst();
	}
	
	/**
	 * Create a BeanInformation object from/based on the specified beanClass.
	 * 
	 * @param beanClass
	 *            The type of the object the BeanInformation information should be about.
	 * 
	 * @return Information about the specified type, encapsulated in a BeanInformation object.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass is deemed illegal. For example, if it is null.
	 * @throws BeanInformationException
	 *             If a problem occurs when creating BeanInformation about the specified type. This may be because the
	 *             specified type is not a valid JavaBean, or perhaps because an unexpected exception occurred when
	 *             trying to gather information about the type.
	 */
	BeanInformation create(Class<?> beanClass) throws IllegalArgumentException, BeanInformationException;

}
