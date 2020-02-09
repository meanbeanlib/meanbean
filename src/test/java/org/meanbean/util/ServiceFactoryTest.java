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

import org.junit.Test;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceFactoryTest {

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

	private List<BeanInformationFactory> getAll() {
		return BeanInformationFactory.getServiceDefinition()
				.getServiceFactory()
				.getAll();
	}

}
