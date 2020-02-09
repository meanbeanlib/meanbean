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

package org.meanbean.test.beans;

import org.meanbean.lang.EquivalentFactory;

import java.util.Calendar;
import java.util.Date;

/**
 * Factory that creates logically equivalent ComplexBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class ComplexBeanFactory implements EquivalentFactory<ComplexBean> {

	private static final Date DATE_OF_BIRTH;
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2010, 7, 12, 16, 31, 1);
		DATE_OF_BIRTH = calendar.getTime();
	}

	@Override
    public ComplexBean create() {
		ComplexBean bean = new ComplexBean();
		bean.setId(1);
		bean.setFirstName("TEST_FIRST_NAME");
		bean.setLastName("TEST_LAST_NAME");
		bean.setDateOfBirth(DATE_OF_BIRTH);
		bean.setFavouriteNumber(1234);
		return bean;
	}
}
