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

/**
 * Utility methods for assertions.
 * 
 * @author Graham Williamson
 */
public final class AssertionUtils {

	/**
	 * Construct a new AssertionUtils.
	 */
	private AssertionUtils() {
		// Do nothing - make non-instantiable
	}

	/**
	 * Fail an assertion, which will throw an <code>AssertionError</code> with no message.
	 */
	public static void fail() {
		throw new AssertionError();
	}

	/**
	 * Fail an assertion, which will throw an <code>AssertionError</code> with the specified message.
	 * 
	 * @param message
	 *            A message detailing the assertion failure.
	 */
	public static void fail(String message) {
		throw new AssertionError(message);
	}
}
