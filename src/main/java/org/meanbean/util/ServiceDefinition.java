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

import java.util.Arrays;

/**
 * Defines how service implementations should be instantiated
 */
public class ServiceDefinition<T> {

	private Class<T> serviceType;
	private Class<?>[] constructorTypes = {};
	private Object[] constructorArgs = {};

	public ServiceDefinition(Class<T> serviceType) {
		this(serviceType, new Class<?>[] {}, new Object[] {});
	}

	public ServiceDefinition(Class<T> serviceType, Class<?>[] constructorTypes, Object[] constructorArgs) {
		if (constructorTypes.length != constructorArgs.length) {
			throw new IllegalArgumentException();
		}
		this.serviceType = serviceType;
		this.constructorTypes = Arrays.copyOf(constructorTypes, constructorTypes.length);
		this.constructorArgs = Arrays.copyOf(constructorArgs, constructorTypes.length);
	}

	Class<T> getServiceType() {
		return serviceType;
	}

	Class<?>[] getConstructorTypes() {
		return constructorTypes;
	}

	Object[] getConstructorArgs() {
		return constructorArgs;
	}

	public ServiceFactory<T> getServiceFactory(){
		return ServiceFactory.getInstance(this);
	}
}
