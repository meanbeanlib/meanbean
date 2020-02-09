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

package org.meanbean.bean.info;

import java.util.Collection;

/**
 * Defines an object that provides information about a JavaBean.
 * 
 * @author Graham Williamson
 */
public interface BeanInformation {

	/**
	 * Get the type of bean this object contains information about.
	 * 
	 * @return The type of bean this object contains information about.
	 */
	Class<?> getBeanClass();

	/**
	 * Get the names of all properties of the bean.
	 * 
	 * @return A Collection of names of all properties of the bean.
	 */
	Collection<String> getPropertyNames();

	/**
	 * Get information about all properties of the bean.
	 * 
	 * @return A Collection of all properties of the bean.
	 */
	Collection<PropertyInformation> getProperties();

}
