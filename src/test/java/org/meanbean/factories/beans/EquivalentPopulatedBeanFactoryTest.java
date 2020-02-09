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

package org.meanbean.factories.beans;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.factories.equivalent.EquivalentPopulatedBeanFactory;
import org.meanbean.factories.util.BasicFactoryLookupStrategy;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.beans.ComplexBean;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EquivalentPopulatedBeanFactoryTest {

	@Mock
	private BeanInformation beanInformationMock;
	
	@Mock
	private FactoryLookupStrategy factoryLookupStrategyMock;

	private BeanInformation beanInformationReal;

	private FactoryLookupStrategy factoryLookupStrategyReal;

	private static final String ID_KEY = "id";

	private static final String FIRST_NAME_KEY = "firstName";

	private static final String LAST_NAME_KEY = "lastName";

	private static final String DATE_OF_BIRTH_KEY = "dateOfBirth";

	private static final String FAVOURITE_NUMBER_KEY = "favouriteNumber";

	private static final Long TEST_ID = 1234L;

	private static final String TEST_FIRST_NAME = "TEST_FIRST_NAME";

	private static final String TEST_LAST_NAME = "TEST_LAST_NAME";

	private static final Date TEST_DATE_OF_BIRTH = new Date();

	private static final Long TEST_FAVOURITE_NUMBER = 17L;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setup() {
		BeanTester tester = new BeanTester();
		factoryLookupStrategyReal =
		        new BasicFactoryLookupStrategy(tester.getFactoryCollection(), tester.getRandomValueGenerator());
		BeanInformationFactory javaBeanInformationFactory = new JavaBeanInformationFactory();
		beanInformationReal = javaBeanInformationFactory.create(ComplexBean.class);
		when(factoryLookupStrategyMock.getFactory(beanInformationReal, getProperty(ID_KEY), null))
				.thenReturn((Factory) () -> TEST_ID);
		when(factoryLookupStrategyMock.getFactory(beanInformationReal, getProperty(FIRST_NAME_KEY), null))
				.thenReturn((Factory) () -> TEST_FIRST_NAME);
		when(factoryLookupStrategyMock.getFactory(beanInformationReal, getProperty(LAST_NAME_KEY), null))
				.thenReturn((Factory) () -> TEST_LAST_NAME);
		when(factoryLookupStrategyMock.getFactory(beanInformationReal, getProperty(DATE_OF_BIRTH_KEY), null))
				.thenReturn((Factory) () -> TEST_DATE_OF_BIRTH);
		when(factoryLookupStrategyMock.getFactory(beanInformationReal, getProperty(FAVOURITE_NUMBER_KEY), null))
				.thenReturn((Factory) () -> TEST_FAVOURITE_NUMBER);
	}

	private PropertyInformation getProperty(String name) {
		return beanInformationReal.getProperties()
				.stream()
				.filter(property -> property.getName().equals(name))
				.findFirst()
				.get();
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullBeanInformation() throws Exception {
		new EquivalentPopulatedBeanFactory(null, factoryLookupStrategyMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullFactoryLookupStrategy() throws Exception {
		new EquivalentPopulatedBeanFactory(beanInformationMock, null);
	}

	@Test
	public void constructorShouldPermitNonNullArguments() throws Exception {
		new EquivalentPopulatedBeanFactory(beanInformationMock, factoryLookupStrategyMock);
	}

	@Test
	public void createShouldReturnNewObjectEachInvocation() throws Exception {
		EquivalentPopulatedBeanFactory factory =
		        new EquivalentPopulatedBeanFactory(beanInformationReal, factoryLookupStrategyReal);
		assertThat("Should be different instances.", factory.create(), is(not(sameInstance(factory.create()))));
	}

	@Test
	public void createdObjectPropertiesShouldBeSameAsThatGeneratedByFactories() throws Exception {
		EquivalentPopulatedBeanFactory factory =
		        new EquivalentPopulatedBeanFactory(beanInformationReal, factoryLookupStrategyMock);
		ComplexBean bean = (ComplexBean) factory.create();
		assertThat(bean.getFirstName(), is(TEST_FIRST_NAME));
		assertThat(bean.getLastName(), is(TEST_LAST_NAME));
		assertThat(bean.getFavouriteNumber(), is(TEST_FAVOURITE_NUMBER));
		assertThat(bean.getDateOfBirth(), is(TEST_DATE_OF_BIRTH));
	}

	@Test
	public void createShouldCreateLogicallyEquivalentObjects() throws Exception {
		EquivalentPopulatedBeanFactory factory =
		        new EquivalentPopulatedBeanFactory(beanInformationReal, factoryLookupStrategyMock);
		ComplexBean bean1 = (ComplexBean) factory.create();
		ComplexBean bean2 = (ComplexBean) factory.create();
		ComplexBean bean3 = (ComplexBean) factory.create();
		assertThat(bean1, is(bean2));
		assertThat(bean2, is(bean3));
	}
}
