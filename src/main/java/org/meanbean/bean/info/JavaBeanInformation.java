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

import org.meanbean.util.ValidationHelper;
import org.meanbean.util.reflect.ReflectionAccessor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Character.toLowerCase;

/**
 * Concrete BeanInformation that gathers and contains information about a JavaBean by using java.beans.BeanInfo.
 * 
 * @author Graham Williamson
 */
class JavaBeanInformation implements BeanInformation {

	/** The type of object this object contains information about. */
	private final Class<?> beanClass;

	/** The mechanism used to acquire information about the type. */
	private final BeanInfo beanInfo;

	/** Information about each property of the type, keyed by property name. */
	private final Map<String, PropertyInformation> properties = new ConcurrentHashMap<>();

	/**
	 * Construct a new JavaBean Information object for the specified type.
	 * 
	 * @param beanClass
	 *            The type of the JavaBean object to gather information about.
	 * 
	 * @throws IllegalArgumentException
	 *             If the beanClass is deemed illegal. For example, if it is null.
	 * 
	 * @throws BeanInformationException
	 *             If a problem occurred when gathering information about the specified type. This may be because the
	 *             specified type is not a valid JavaBean.
	 */
	JavaBeanInformation(Class<?> beanClass) throws IllegalArgumentException, BeanInformationException {
		ValidationHelper.ensureExists("beanClass", "gather JavaBean information", beanClass);
		this.beanClass = beanClass;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			throw new BeanInformationException("Failed to acquire information about beanClass [" + beanClass + "].", e);
		}
		initialize();
	}

	/**
	 * Initialize this object ready for public use. This involves acquiring information about each property of the type.
	 */
	private void initialize() {
		Map<String, Method> fluentWriteMethods = findFluentWriteMethods(beanInfo);
		for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
			if ("class".equals(propertyDescriptor.getName())) {
				continue;
			}
			PropertyDescriptorPropertyInformation propertyInformation = new PropertyDescriptorPropertyInformation(propertyDescriptor);
			Method fluentWriteMethod = fluentWriteMethods.get(propertyDescriptor.getName());
			if (!propertyInformation.isWritable() && fluentWriteMethod != null) {
				propertyInformation.setWriteMethodOverride(fluentWriteMethod);
			}

			if (propertyInformation.isReadableWritable()) {
				makeAccessible(propertyInformation.getReadMethod());
				makeAccessible(propertyInformation.getWriteMethod());
			}
			properties.put(propertyInformation.getName(), propertyInformation);
		}
	}

	private Map<String, Method> findFluentWriteMethods(BeanInfo beanInfo) {
		List<Method> methods = findCandidateWriteMethods(beanInfo.getMethodDescriptors());
		Map<String, Method> fluentMethods = new HashMap<>();
		for (Method method : methods) {
			String propertyName = method.getName().substring(3);
			propertyName = toLowerCase(propertyName.charAt(0)) + propertyName.substring(1);
			fluentMethods.put(propertyName, method);
		}
		return fluentMethods;
	}

	// based on spring 5
	private List<Method> findCandidateWriteMethods(MethodDescriptor... methodDescriptors) {
		List<Method> matches = new ArrayList<>();
		for (MethodDescriptor methodDescriptor : methodDescriptors) {
			Method method = methodDescriptor.getMethod();
			if (isCandidateWriteMethod(method)) {
				matches.add(method);
			}
		}
		// Sort non-void returning write methods to guard against the ill effects of
		// non-deterministic sorting of methods returned from Class#getDeclaredMethods
		// under JDK 7. See https://bugs.java.com/view_bug.do?bug_id=7023180
		matches.sort((m1, m2) -> m2.toString().compareTo(m1.toString()));
		return matches;
	}

	// based on spring 5
	private static boolean isCandidateWriteMethod(Method method) {
		String methodName = method.getName();
		int nParams = method.getParameterCount();
		return methodName.length() > 3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) &&
				!void.class.isAssignableFrom(method.getReturnType()) &&
				nParams == 1;
	}

	private void makeAccessible(Method method) {
		if (!method.isAccessible()) {
			ReflectionAccessor.getInstance().makeAccessible(method);
		}
	}

	/**
	 * Get the type of bean this object contains information about.
	 * 
	 * @return The type of bean this object contains information about.
	 */
	@Override
	public Class<?> getBeanClass() {
		return beanClass;
	}

	/**
	 * Get the names of all properties of the bean.
	 * 
	 * @return A Collection of names of all properties of the bean.
	 */
	@Override
	public Collection<String> getPropertyNames() {
		return properties.keySet();
	}

	/**
	 * Get information about all properties of the bean.
	 * 
	 * @return A Collection of all properties of the bean.
	 */
	@Override
	public Collection<PropertyInformation> getProperties() {
		return properties.values();
	}
}
