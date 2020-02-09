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

import java.util.Map;

import org.meanbean.bean.info.BeanInformation;

/**
 * Defines an object that affords functionality to populate a bean (set its fields) with specified values.
 * 
 * @author Graham Williamson
 */
public interface BeanPopulator {

	/**
	 * Populate the specified bean with the specified values. Values are keyed by property name (e.g. "firstName") and
	 * are matched to properties on the bean and their setters (e.g. "setFirstName"). Only properties with a setter
	 * method and an entry in the values map will be set. Any entries in the values map that do not exist on the bean
	 * are ignored.
	 * 
	 * @param bean
	 *            The object to populate.
	 * @param beanInformation
	 *            Information about the object to populate.
	 * @param values
	 *            The values to populate the object with, keyed by property name (e.g. "firstName").
	 * 
	 * @throws IllegalArgumentException
	 *             If any of the parameters are deemed illegal. For example, if any are null.
	 * @throws BeanPopulationException
	 *             If an error occurs when populating the object.
	 */
	public void populate(Object bean, BeanInformation beanInformation, Map<String, Object> values)
	        throws IllegalArgumentException, BeanPopulationException;
}
