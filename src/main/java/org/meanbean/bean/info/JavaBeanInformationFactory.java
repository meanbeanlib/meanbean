package org.meanbean.bean.info;

import org.kohsuke.MetaInfServices;

/**
 * Concrete BeanInformationFactory that creates concrete JavaBeanInformation objects from/based on a specified
 * beanClass.
 * 
 * @author Graham Williamson
 */
@MetaInfServices
public class JavaBeanInformationFactory implements BeanInformationFactory {

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
	@Override
    public BeanInformation create(Class<?> beanClass) throws IllegalArgumentException, BeanInformationException {
		return new JavaBeanInformation(beanClass);
	}
}