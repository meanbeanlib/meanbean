package org.meanbean.bean.info;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JavaBeanInformationFactoryTest {

    @Test
    public void shouldCreateCorrectJavaBeanInformation() throws Exception {
        // Given
        JavaBeanInformationFactory factory = new JavaBeanInformationFactory();
        Class<String> inputBeanClass = String.class;
        // When
        BeanInformation beanInformation = factory.create(inputBeanClass);
        // Then
        assertEquals("Incorrect BeanInformation.", beanInformation.getBeanClass(), inputBeanClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventNullBeanClass() throws Exception {
        // Given
        JavaBeanInformationFactory factory = new JavaBeanInformationFactory();
        // When
        factory.create(null);
        // Then - throws IllegalArgumentException
    }
}