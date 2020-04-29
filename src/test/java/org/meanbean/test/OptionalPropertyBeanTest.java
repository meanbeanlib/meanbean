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
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.factories.OptionalFactoryLookup;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalPropertyBeanTest {

	private FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();
	private BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	@Test
	public void test() {
		BeanVerifier.forClass(OptionalPropertyBean.class)
				.verifyGettersAndSetters();

		BeanInformation beanInformation = beanInformationFactory.create(OptionalPropertyBean.class);
		Collection<PropertyInformation> properties = beanInformation.getProperties();
		for (PropertyInformation propertyInformation : properties) {
			Factory<?> factory = factoryLookupStrategy.getFactory(beanInformation, propertyInformation, null);

			assertThat(factory.getClass().getName())
					.contains(OptionalFactoryLookup.class.getName());
		}
	}

	public static class OptionalPropertyBean {

		private OptionalLong num1;
		private OptionalInt num2;
		private OptionalDouble num3;
		private Double num4;
		private List<Integer> string;

		public OptionalLong getNum1() {
			return num1;
		}

		public void setNum1(OptionalLong num1) {
			this.num1 = num1;
		}

		public OptionalInt getNum2() {
			return num2;
		}

		public void setNum2(OptionalInt num2) {
			this.num2 = num2;
		}

		public OptionalDouble getNum3() {
			return num3;
		}

		public void setNum3(OptionalDouble num3) {
			this.num3 = num3;
		}

		public OptionalDouble getNum4() {
			return num4 == null ? OptionalDouble.empty() : OptionalDouble.of(num4);
		}

		public void setNum4(Double num4) {
			this.num4 = num4;
		}

		public Optional<List<Integer>> getString() {
			return Optional.ofNullable(string);
		}

		public void setString(Optional<List<Integer>> string) {
			this.string = string.orElse(null);
		}

	}
}
