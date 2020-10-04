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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.ArrayPropertyBeanWithConstructor;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.ComplexBean;
import org.meanbean.test.beans.PackagePrivateConstructorObject;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceFactory;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BeanTesterTest {

	private BeanTester beanTester;

	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	@Mock
	private Configuration configuration;

	@Before
	public void before() {
		ServiceFactory.clear();
		beanTester = new BeanTester();
	}

	@Test
	public void shouldGetRandomValueGenerator() throws Exception {
		RandomValueGenerator randomValueGenerator = beanTester.getRandomValueGenerator();
		assertThat("Failed to get RandomValueGenerator.", randomValueGenerator, is(not(nullValue())));
		randomValueGenerator.nextBoolean();
	}

	@Test
	public void shouldGetFactoryRepository() throws Exception {
		FactoryCollection factoryRepository = beanTester.getFactoryCollection();
		assertThat("Failed to get FactoryRepository.", factoryRepository, is(not(nullValue())));
		Factory<String> stringFactory = factoryRepository.getFactory(String.class);
		String randomString = stringFactory.create();
		assertThat("Failed to get random String from FactoryRepository.", randomString, is(not(nullValue())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBeanThatTakesBeanClassShouldPreventNullBeanClass() throws Exception {
		beanTester.testBean(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBeanThatTakesBeanClassAndConfigurationShouldPreventNullBeanClass() throws Exception {
		beanTester.testBean((Class<?>) null, configuration);
	}

	@Test
	public void testBeanThatTakesBeanClassAndConfigurationShouldPermitNullConfiguration() throws Exception {
		beanTester.testBean(Bean.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBeanThatTakesBeanInformationAndConfigurationShouldPreventNullBeanInformation() throws Exception {
		beanTester.testBean((BeanInformation) null, configuration);
	}

	@Test
	public void testBeanThatTakesBeanInformationAndConfigurationShouldPermitNullConfiguration() throws Exception {
		BeanInformation beanInformation = beanInformationFactory.create(Bean.class);
		beanTester.testBean(beanInformation, null);
	}

	@Test
	public void testBeanShouldBeAbleToTestBeansWithPackagePrivateConstructors() throws Exception {
		beanTester.testBean(PackagePrivateConstructorObject.class);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassShouldThrowAssertionErrorForBadGetterBean() throws Exception {
		beanTester.testBean(BeanWithBadGetterMethod.class);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassShouldThrowAssertionErrorForBadSetterBean() throws Exception {
		beanTester.testBean(BeanWithBadSetterMethod.class);
	}

	@Test
	public void testBeanThatTakesBeanClassShouldNotThrowAssertionErrorWhenGettersAndSettersFunctionCorrectly()
	        throws Exception {
		beanTester.testBean(ComplexBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassAndConfigurationShouldThrowAssertionErrorForBadGetterBean() throws Exception {
		beanTester.testBean(BeanWithBadGetterMethod.class, configuration);
	}

	@Test(expected = AssertionError.class)
	public void testBeanThatTakesBeanClassAndConfigurationShouldThrowAssertionErrorForBadSetterBean() throws Exception {
		beanTester.testBean(BeanWithBadSetterMethod.class, configuration);
	}

	@Test
	public void testBeanThatTakesBeanClassAndConfigurationShouldNotThrowAssertionErrorWhenGettersAndSettersFunctionCorrectly()
	        throws Exception {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.overrideFactory("lastName", () -> "LastName" + System.currentTimeMillis());
		configurationBuilder.overrideFactory("dateOfBirth", Date::new);
		beanTester.testBean(ComplexBean.class, configurationBuilder.build());
	}

	@Test
	public void testBeanThatTakesBeanClassAndConfigurationShouldIgnoreBadPropertyWhenToldTo() throws Exception {
		beanTester.testBean(BadComplexBean.class, new ConfigurationBuilder().ignoreProperty("lastName").build());
	}

	@Test
	public void verifyCustomFactoriesFirst() {
		verifyCustomFactory();

		verifyNoCustomFactory();
	}

	@Test
	public void verifyCustomFactoriesLast() {
		verifyNoCustomFactory();

		verifyCustomFactory();
	}

	private void verifyNoCustomFactory() {
		assertThatCode(() -> {
			BeanTester beanTester = new BeanTester();
			beanTester.testBean(ArrayPropertyBeanWithConstructor.class);
		}).hasMessageContaining("Failed to instantiate");
	}

	private void verifyCustomFactory() {
		BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection().addFactory(ArrayPropertyBeanWithConstructor.class, () -> {
			RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();
			byte[] randomBytes = randomValueGenerator.nextBytes(8);
			return new ArrayPropertyBeanWithConstructor(randomBytes);
		});
		beanTester.testBean(ArrayPropertyBeanWithConstructor.class);
	};

	// TODO TEST COMBINATIONS WITH CONFIGURATIONS AND BEAN INFORMATIONS ETC

	public static class BeanWithBadGetterMethod extends Bean {
		@Override
		public String getName() {
			return "FIXED_TEST_STRING";
		}
	}

	public static class BeanWithBadSetterMethod extends Bean {
		@Override
		public void setName(String name) {
			// do nothing
		}
	}

	public static class BadComplexBean extends ComplexBean {
		@Override
		public String getLastName() {
			return "FIXED_TEST_STRING";
		}
	}
}
