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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BeanVerifierTest {

	@Test
	public void verifyBean() {
		BeanVerifier.verifyBean(EmployeeId.class);
	}

	@Test(expected = AssertionError.class)
	public void verifyBeanFail() {
		BeanVerifier.verifyBean(Company.class);
	}

	@Test
	public void verifyBeans() {
		BeanVerifier.verifyBeans(EmployeeId.class, Bean.class);
	}

	@Test(expected = AssertionError.class)
	public void verifyBeansFail() {
		BeanVerifier.verifyBeans(EmployeeId.class, Company.class);
	}

	@Test
	public void verifyJavaBean() {
		BeanVerifier.forClass(Company.class)
				.verifyGettersAndSetters();
	}

	@Test
	public void verifyJavaBeansWithSettings() {
		Company company = spy(new Company());
		BeanVerifier.forClass(Company.class)
				.withSettings(settings -> settings.setDefaultIterations(12))
				.withSettings(settings -> settings.addEqualsInsignificantProperty(Company::getId))
				.withSettings(settings -> settings.registerFactory(Company.class, () -> company)) // so that the same spy is reused
				.withSettings(settings -> settings.addIgnoredProperty(Company::getName))
				.verifyGettersAndSetters();

		verify(company, never()).getName();
		verify(company, never()).setName(anyString());
		verify(company, atLeastOnce()).setCompanyNumber(anyString());
	}

	@Test
	public void verifyJavaBeansEditSettings() {
		Company company = spy(new Company());
		BeanVerifier.forClass(Company.class)
				.editSettings()
				.setDefaultIterations(12)
				.addEqualsInsignificantProperty(Company::getId)
				.registerFactory(Company.class, () -> company)
				.addIgnoredProperty(Company::getName)
				.edited()
				.verifyGettersAndSetters();

		verify(company, never()).getName();
		verify(company, never()).setName(anyString());
		verify(company, atLeastOnce()).setCompanyNumber(anyString());
	}

	@Test(expected = BeanTestException.class)
	public void verifyJavaBeanFail() {
		BeanVerifier.forClass(NonBean.class)
				.verifyGettersAndSetters();
	}

	@Test
	public void verifyJavaBeanEqualsHashCode() {
		BeanVerifier.forClass(Company.class)
				.editSettings()
				.addEqualsInsignificantProperty(Company::getId)
				.setDefaultIterations(10)
				.edited()
				.verifyGettersAndSetters()
				.verifyEqualsAndHashCode();

		BeanVerifier.forClass(Company.class)
				.withSettings(settings -> settings.addEqualsInsignificantProperty(Company::getId))
				.verifyGettersAndSetters()
				.verifyEqualsAndHashCode();
	}

	@Test
	public void verifyPackage() {
		BeanVerifier.verifyBeansIn(ScanBean.class.getPackage());
	}
}
