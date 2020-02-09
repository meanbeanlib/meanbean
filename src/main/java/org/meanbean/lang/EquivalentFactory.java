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

package org.meanbean.lang;

/**
 * Defines an object that creates logically equivalent objects of a specified type.
 * 
 * @author Graham Williamson
 * @param <T>
 *            The data type of the object this Factory creates.
 */
public interface EquivalentFactory<T extends Object> {

	/**
	 * Create a new logically equivalent object of the specified type.
	 * 
	 * @return A new object of the specified type.
	 */
	T create();
}
