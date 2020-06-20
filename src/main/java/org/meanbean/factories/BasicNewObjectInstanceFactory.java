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

import org.meanbean.lang.Factory;
import org.meanbean.util.ValidationHelper;
import org.meanbean.util.reflect.ReflectionAccessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Concrete Factory that creates instances of the type of object specified during construction of the Factory. Only
 * classes that have a no-argument constructor can be successfully instantiated by this Factory. If the class does not
 * have a no-argument constructor, an exception will be thrown when <code>create()</code> is invoked.
 * 
 * @author Graham Williamson
 */
public class BasicNewObjectInstanceFactory implements Factory<Object> {

	/** The type of Object this Factory should create new instances of. */
	private final Class<?> clazz;

	public static Factory<Object> findBeanFactory(Class<?> clazz) {
		FactoryCollection factoryCollection = FactoryCollection.getInstance();
		if (factoryCollection.hasFactory(clazz)) {
			return factoryCollection.getFactory(clazz);
		}
		return new BasicNewObjectInstanceFactory(clazz);
	}

	/**
	 * Construct a basic new object instance Factory.
	 * 
	 * @param clazz
	 *            The type of Object the Factory should create new instances of.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified clazz is deemed illegal. For example, if it is null.
	 */
	public BasicNewObjectInstanceFactory(Class<?> clazz) throws IllegalArgumentException {
		ValidationHelper.ensureExists("clazz", "construct Factory", clazz);
		this.clazz = clazz;
	}

	/**
	 * Create a new instance of the type specified during construction of this Factory. The type must have a no-arg
	 * constructor for this to work.
	 * 
	 * @return A new instance of the type specified during construction of this Factory.
	 * 
	 * @throws ObjectCreationException
	 *             If an instance of the type cannot be constructed, perhaps due to it not having a no-arg constructor.
	 */
	@Override
    public Object create() throws ObjectCreationException {
		Object result = null;
		try {
			Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
			ReflectionAccessor.getInstance().makeAccessible(declaredConstructor);
			result = declaredConstructor.newInstance();
		} catch (InstantiationException e) {
			wrapAndRethrowException(e);
		} catch (IllegalAccessException e) {
			wrapAndRethrowException(e);
		} catch (SecurityException e) {
			wrapAndRethrowException(e);
		} catch (NoSuchMethodException e) {
			wrapAndRethrowException(e);
		} catch (IllegalArgumentException e) {
			wrapAndRethrowException(e);
		} catch (InvocationTargetException e) {
			wrapAndRethrowException(e);
		}
		return result;
	}

	/**
	 * Wraps the specified Exception within an ObjectCreationException and throws it.
	 * 
	 * @param exception
	 *            The Exception to wrap and (re)throw.
	 * 
	 * @throws ObjectCreationException
	 *             The ObjectCreationException wrapping the specified Exception.
	 */
	private void wrapAndRethrowException(Exception exception) throws ObjectCreationException {
		String message =
		        "Failed to instantiate object of type [" + clazz.getName() + "] due to "
		                + exception.getClass().getSimpleName() + ".";
		if (exception instanceof NoSuchMethodException) {
			message = message + " Do you need to add a custom Factory?";
		}
		throw new ObjectCreationException(message, exception);
	}
}
