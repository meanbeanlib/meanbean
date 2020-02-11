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
 * Defines an object that affords helpful input validation functionality.
 * 
 * @author Graham Williamson
 */
public final class ValidationHelper {

	/**
	 * <p>
	 * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br>
	 * </p>
	 * 
	 * <p>
	 * The exception thrown will contain the name of the parameter that must exist.
	 * </p>
	 * 
	 * @param name
	 *            The name of the object that must exist.
	 * @param value
	 *            The object that must exist.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified value does not exist.
	 */
	public static void ensureExists(String name, Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Object [" + name + "] must be provided.");
        }
    }

	/**
	 * <p>
	 * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br>
	 * </p>
	 * 
	 * <p>
	 * The exception thrown will contain the name of the parameter that must exist as well as the operation being
	 * attempted.
	 * </p>
	 * 
	 * @param name
	 *            The name of the object that must exist.
	 * @param operation
	 *            The operation being attempted.
	 * @param value
	 *            The object that must exist.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified value does not exist.
	 */
    public static void ensureExists(String name, String operation, Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Cannot " + operation + " with null " + name + ".");
        }
    }
    
    public static void ensure(boolean expr, String msg) {
    	if (!expr) {
    		throw new IllegalArgumentException(msg);
    	}
    }
}
