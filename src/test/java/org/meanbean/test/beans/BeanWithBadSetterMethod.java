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

/**
 * A simple single property bean with correctly implemented equals and hashCode methods, but with a setter that throws a
 * RuntimeException. Since the setter cannot be called, the getter returns a constant value. This class should only be
 * used for testing.
 * 
 * @author Graham Williamson
 */
public class BeanWithBadSetterMethod extends Bean {

	@Override
	public void setName(String name) {
		throw new RuntimeException("BAD SETTER METHOD");
	}

	@Override
	public String getName() {
		return "TEST_VALUE";
	}
}
