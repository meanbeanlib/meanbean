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

package org.meanbean.bean.util;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.util.BasicFactoryLookupStrategy;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.meanbean.test.beans.ComplexBean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.util.RandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class BeanPropertyValuesFactoryTest {

	@Mock
	private BeanInformation beanInformationMock;

	@Mock
	private FactoryLookupStrategy factoryLookupStrategyMock;

	private final BeanTester beanTesterReal = new BeanTester();

	private final FactoryLookupStrategy factoryLookupStrategyReal = new BasicFactoryLookupStrategy(
	        beanTesterReal.getFactoryCollection(), beanTesterReal.getRandomValueGenerator());

	private final BeanInformationFactory beanInformationFactoryReal = new JavaBeanInformationFactory();

	private final BeanInformation complexBeanInformationReal = beanInformationFactoryReal.create(ComplexBean.class);

	@Mock
	private Factory<Long> longFactoryMock;

	@After
	public void after() {
		beanTesterReal.getFactoryCollection().addFactory(long.class, new LongFactory(RandomValueGenerator.getInstance()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullBeanInformation() throws Exception {
		new BeanPropertyValuesFactory(null, factoryLookupStrategyMock, newConfiguration());
	}

    private Configuration newConfiguration() {
        return new ConfigurationBuilder().build();
    }

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullFactoryLookupStrategy() throws Exception {
		new BeanPropertyValuesFactory(beanInformationMock, null, newConfiguration());
	}

	@Test
	public void constructorShouldPermitNonNullParameters() throws Exception {
		new BeanPropertyValuesFactory(beanInformationMock, factoryLookupStrategyMock, newConfiguration());
	}

	@Test
	public void createShouldReturnMapContainingValuesForWritableProperties() throws Exception {
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(complexBeanInformationReal, factoryLookupStrategyReal, newConfiguration());
		Map<String, Object> values = beanPropertyValuesFactory.create();
		assertTrue(values.containsKey("id"));
		assertTrue(values.containsKey("firstName"));
		assertTrue(values.containsKey("lastName"));
		assertTrue(values.containsKey("favouriteNumber"));
		assertTrue(values.containsKey("dateOfBirth"));
		assertFalse(values.containsKey("asString"));
		assertThat(values.get("id"), is(not(nullValue())));
		assertThat(values.get("firstName"), is(not(nullValue())));
		assertThat(values.get("lastName"), is(not(nullValue())));
		assertThat(values.get("favouriteNumber"), is(not(nullValue())));
		assertThat(values.get("dateOfBirth"), is(not(nullValue())));
		assertThat(values.size(), is(5));
	}

	@Test
	public void createShouldReturnEmptyMapWhenObjectHasNoWritableProperties() throws Exception {
		NonBean bean = new NonBean("TEST");
		BeanInformation beanInformation = beanInformationFactoryReal.create(bean.getClass());
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(beanInformation, factoryLookupStrategyReal, newConfiguration());
		Map<String, Object> values = beanPropertyValuesFactory.create();
		assertTrue(values.isEmpty());
	}

	@Test
	public void createShouldReturnNewMapEachInvocation() throws Exception {
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(complexBeanInformationReal, factoryLookupStrategyReal, newConfiguration());
		assertThat("Should be different instances.", beanPropertyValuesFactory.create(),
		        is(not(sameInstance(beanPropertyValuesFactory.create()))));
	}

	@Test(expected = ObjectCreationException.class)
	public void createShouldWrapExceptionsInObjectCreationException() throws Exception {
		doThrow(new RuntimeException("TEST EXCEPTION")).when(longFactoryMock).create();
		beanTesterReal.getFactoryCollection().addFactory(long.class, longFactoryMock);
		BeanPropertyValuesFactory beanPropertyValuesFactory =
		        new BeanPropertyValuesFactory(complexBeanInformationReal, factoryLookupStrategyReal, newConfiguration());
		beanPropertyValuesFactory.create();
	}
}
