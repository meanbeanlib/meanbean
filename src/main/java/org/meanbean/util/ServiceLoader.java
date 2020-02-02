/*
 * Copyright 2016 Martin Winandy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.meanbean.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Alternative service loader that supports constructors with arguments in opposite to {@link java.util.ServiceLoader}.
 *
 * @param <T>
 *            Service interface
 */
public final class ServiceLoader<T> {

	private static final String SERVICE_PREFIX = "META-INF/services/";

	private final Class<? extends T> service;
	private final Class<?>[] argumentTypes;
	private final ClassLoader classLoader;

	/**
	 * @param service
	 *            Service interface
	 * @param argumentTypes
	 *            Expected argument types for constructors
	 */
	public ServiceLoader(Class<? extends T> service, Class<?>... argumentTypes) {
		this.service = service;
		this.argumentTypes = argumentTypes;
		this.classLoader = Stream.of(currentThread().getContextClassLoader(), getClass().getClassLoader())
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(ClassLoader.getSystemClassLoader());
	}

	public List<T> createAll(Object... arguments) {
		try {
			Collection<String> classNames = findClassNames(classLoader, service);
			List<T> services = new ArrayList<>(classNames.size());
			for (String className : classNames) {
				services.add(createInstance(className, arguments));
			}
			return services;
		} catch (Exception ex) {
			throw new ServiceConfigurationError("Cannot create service instance for " + service.getName(), ex);
		}
	}

	private static <T> Collection<String> findClassNames(ClassLoader classLoader, Class<? extends T> service) throws IOException {
		String name = SERVICE_PREFIX + service.getName();
		Enumeration<URL> urls = classLoader.getResources(name);
		Collection<String> classNames = new LinkedHashSet<>();

		for (URL url : Collections.list(urls)) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))) {
				reader.lines()
						.map(String::trim)
						.filter(line -> !line.isEmpty())
						.filter(line -> line.charAt(0) != '#')
						.forEach(classNames::add);
			}
		}

		return classNames;
	}

	private T createInstance(String className, Object... arguments) throws Exception {
		Class<?> implementation = Class.forName(className, false, classLoader);
		Constructor<?> constructor = implementation.getDeclaredConstructor(argumentTypes);
		Object newInstance = constructor.newInstance(arguments);
		return service.cast(newInstance);
	}

}
