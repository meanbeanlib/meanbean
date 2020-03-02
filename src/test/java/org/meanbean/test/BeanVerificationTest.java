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

package org.meanbean.test;

import org.junit.Test;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.domain.Company;
import org.meanbean.test.beans.domain.EmployeeId;
import org.meanbean.test.beans.scan.ScanBean;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BeanVerificationTest {

    @Test
    public void verifyBean() {
        BeanVerification.verifyBean(EmployeeId.class);
    }

    @Test(expected = AssertionError.class)
    public void verifyBeanFail() {
        BeanVerification.verifyBean(Company.class);
    }

    @Test
    public void verifyBeans() {
        BeanVerification.verifyBeans(EmployeeId.class, Bean.class);
    }

    @Test(expected = AssertionError.class)
    public void verifyBeansFail() {
        BeanVerification.verifyBeans(EmployeeId.class, Company.class);
    }

    @Test
    public void verifyJavaBean() {
        BeanVerification.verifyThat(Company.class)
                .isValidJavaBean();
    }

    @Test
    public void verifyJavaBeanCustomized() {
        Company company = spy(new Company());
        BeanVerification.verifyThat(Company.class)
                .with(customizer -> customizer.setDefaultIterations(1))
                .with(customizer -> customizer.addEqualsInsignificantProperty(Company::getId))
                .with(customizer -> customizer.registerFactory(Company.class, () -> company))
                .with(customizer -> customizer.addIgnoredProperty(Company::getName))
                .isValidJavaBean();

        verify(company, never()).getName();
        verify(company, never()).setName(anyString());
        verify(company).setCompanyNumber(anyString());
    }

    @Test(expected = BeanTestException.class)
    public void verifyJavaBeanFail() {
        BeanVerification.verifyThat(NonBean.class)
                .isValidJavaBean();
    }

    @Test
    public void verifyJavaBeanEqualsHashCode() {
        BeanVerification.verifyThat(Company.class)
                .with(customizer -> customizer.addEqualsInsignificantProperty(Company::getId))
                .isValidJavaBean()
                .hasValidEqualsMethod()
                .hasValidHashCodeMethod();
    }

    @Test
    public void verifyPackage() {
        BeanVerification.verifyBeansIn(ScanBean.class.getPackage());
    }
}
