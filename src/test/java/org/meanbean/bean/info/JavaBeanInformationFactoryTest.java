package org.meanbean.bean.info;

import static org.junit.Assert.*;

import org.junit.Test;

public class JavaBeanInformationFactoryTest {

	private final JavaBeanInformationFactory factory = new JavaBeanInformationFactory();

	@Test
    public void shouldCreate() throws Exception {
	    JavaBeanInformationFactory factory = new JavaBeanInformationFactory();
	    Class<String> inputBeanClass = String.class;
		BeanInformation beanInformation = factory.create(inputBeanClass);
	    assertEquals("Incorrect BeanInformation.", (Class<?>) beanInformation.getBeanClass(), inputBeanClass);
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void shouldPreventNullBeanClass() throws Exception {
	    factory.create(null);
    }
}