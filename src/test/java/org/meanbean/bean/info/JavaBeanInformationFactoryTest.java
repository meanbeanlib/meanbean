/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

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
