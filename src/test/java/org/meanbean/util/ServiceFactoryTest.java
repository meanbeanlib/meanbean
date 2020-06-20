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

package org.meanbean.util;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.FluentPropertyBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ServiceFactoryTest {

	@Before
	public void setUp() {
		ServiceFactory.createContext(this);
	}
	
	@Test
	public void loadSingleImplementor() throws Exception {
		List<BeanInformationFactory> services = getAll();

		assertThat(services)
				.hasOnlyElementsOfType(JavaBeanInformationFactory.class)
				.hasSize(1);
	}

	@Test
	public void getInstanceCaches() throws Exception {
		List<BeanInformationFactory> services1 = getAll();
		List<BeanInformationFactory> services2 = getAll();

		assertThat(services1)
				.isSameAs(services2);
	}

	@Test
	public void testServiceFactoryScopeSpecialFirst() {
		Object contextKey1 = new Object();
		ServiceFactory.createContext(contextKey1);
		verifySpecialRegistration();

		Object contextKey2 = new Object();
		ServiceFactory.createContext(contextKey2);
		verifyNoSpecialRegistration();
	}

	@Test
	public void testServiceFactoryScopeSpecialLast() {
		Object contextKey1 = new Object();
		ServiceFactory.createContext(contextKey1);
		verifyNoSpecialRegistration();

		Object contextKey2 = new Object();
		ServiceFactory.createContext(contextKey2);
		verifySpecialRegistration();
	}

	private void verifySpecialRegistration() {
		FactoryCollection collection = FactoryCollection.getInstance();
		collection.addFactory(FluentPropertyBean.class, () -> {
			throw new IllegalStateException("forced exception");
		});

		Factory<Object> factory = collection.getFactory(FluentPropertyBean.class);
		assertThatCode(() -> {
			factory.create();
		}).hasMessageContaining("forced exception");
	}

	private void verifyNoSpecialRegistration() {
		assertThatCode(() -> {
			FactoryCollection collection = FactoryCollection.getInstance();
			Factory<Object> factory = collection.getFactory(FluentPropertyBean.class);
			factory.create();
		}).isInstanceOf(NoSuchFactoryException.class);
	}

	private List<BeanInformationFactory> getAll() {
		return BeanInformationFactory.getServiceDefinition()
				.getServiceFactory()
				.getAll();
	}

}
