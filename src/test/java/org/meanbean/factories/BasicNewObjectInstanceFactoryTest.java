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

package org.meanbean.factories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.PackagePrivateConstructorObject;
import org.meanbean.test.beans.PrivateConstructorObject;

public class BasicNewObjectInstanceFactoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullRandomNumberGenerator() throws Exception {
		new BasicNewObjectInstanceFactory(null);
	}

	@Test
	public void createShouldReturnNewObjectEachInvocation() throws Exception {
		BasicNewObjectInstanceFactory factory = new BasicNewObjectInstanceFactory(Bean.class);
		Object createdObject1 = factory.create();
		Object createdObject2 = factory.create();
		assertThat("Factory does not create new objects.", createdObject1, is(not(sameInstance(createdObject2))));
	}

	@Test(expected = ObjectCreationException.class)
	public void createWillThrowObjectCreationExceptionWhenClassDoesNotHaveNoArgConstructor() throws Exception {
		new BasicNewObjectInstanceFactory(NonBean.class).create();
	}

	@Test
	public void createShouldReturnNewObjectEvenWhenClassHasPrivateConstructor() throws Exception {
		Object createdObject = new BasicNewObjectInstanceFactory(PrivateConstructorObject.class).create();
		assertThat("Factory failed to create non-null object.", createdObject, is(not(nullValue())));
	}

	@Test
	public void createShouldReturnNewObjectEvenWhenClassHasPackagePrivateConstructor() throws Exception {
		Object createdObject = new BasicNewObjectInstanceFactory(PackagePrivateConstructorObject.class).create();
		assertThat("Factory failed to create non-null object.", createdObject, is(not(nullValue())));
	}
}
