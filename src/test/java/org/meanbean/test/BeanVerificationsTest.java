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

public class BeanVerificationsTest {

	@Test
	public void verifyBean() {
		BeanVerifications.verifyBean(EmployeeId.class);
	}

	@Test(expected = AssertionError.class)
	public void verifyBeanFail() {
		BeanVerifications.verifyBean(Company.class);
	}

	@Test
	public void verifyBeans() {
		BeanVerifications.verifyBeans(EmployeeId.class, Bean.class);
	}

	@Test(expected = AssertionError.class)
	public void verifyBeansFail() {
		BeanVerifications.verifyBeans(EmployeeId.class, Company.class);
	}

	@Test
	public void verifyJavaBean() {
		BeanVerifications.verifyThat(Company.class)
				.isValidJavaBean();
	}

	@Test(expected = BeanTestException.class)
	public void verifyJavaBeanFail() {
		BeanVerifications.verifyThat(NonBean.class)
				.isValidJavaBean();
	}

	@Test
	public void verifyJavaBeanEqualsHashCode() {
		BeanVerifications.verifyThat(Company.class)
				.with(builder -> builder.addEqualsInsignificantProperty(Company.class, Company::getId))
				.isValidJavaBean()
				.hasValidEqualsMethod()
				.hasValidHashCodeMethod();
	}

	@Test
	public void verifyPackage() {
		BeanVerifications.verifyBeansIn(ScanBean.class.getPackage());
	}
}
