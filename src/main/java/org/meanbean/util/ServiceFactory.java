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

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads service through META-INF/services mechanism, additionally providing caching and ordering behavior
 */
public class ServiceFactory<T> {

	private static final ServiceContextMap serviceContextMap = new ServiceContextMap();

	private final List<T> services;

	@SuppressWarnings("unchecked")
	static synchronized <T> ServiceFactory<T> getInstance(ServiceDefinition<T> definition) {
		String inprogressKey = "Load of " + definition.getServiceType().getName() + " already in progress";

		Map<String, Object> contextMap = serviceContextMap.getContextMap();
		if (contextMap.containsKey(inprogressKey)) {
			throw new IllegalStateException(inprogressKey);
		}

		contextMap.put(inprogressKey, inprogressKey);
		try {
			return (ServiceFactory<T>) contextMap.computeIfAbsent(
					definition.getServiceType().getName(),
					key -> ServiceFactory.create(definition));
		} finally {
			contextMap.remove(inprogressKey);
		}
	}
	
	public static Void createContext(Object key) {
		serviceContextMap.createContext(key);
		return null;
	}

	public static Void createContextIfNeeded(Object key) {
		serviceContextMap.createContextIfNeeded(key);
		return null;
	}
	
	public static boolean hasContext() {
		return serviceContextMap.hasContext();
		//ServiceContextMap.currentKey.get() != null;
	}
	
	public static void clear() {
		serviceContextMap.clear();
	}

	static <T> ServiceFactory<T> create(ServiceDefinition<T> definition) {
		List<T> services = ServiceFactory.doLoad(definition);
		return new ServiceFactory<>(services, definition);
	}

	private ServiceFactory(List<T> services, ServiceDefinition<T> definition) {
		if (services.isEmpty()) {
			throw new IllegalArgumentException("cannot find services for " + definition.getServiceType());
		}
		this.services = Collections.unmodifiableList(services);
	}

	public T getFirst() {
		return getAll().get(0);
	}

	public List<T> getAll() {
		return services;
	}

	private static synchronized <T> List<T> doLoad(ServiceDefinition<T> serviceDefinition) {
		ServiceLoader<T> loader = new ServiceLoader<>(serviceDefinition.getServiceType(),
				serviceDefinition.getConstructorTypes());
		List<T> services = loader.createAll(serviceDefinition.getConstructorArgs());
		Collections.sort(services, getComparator());
		return services;
	}

	public static <T> Comparator<T> getComparator() {
		return Comparator.comparingInt(ServiceFactory::getOrder);
	}

	private static <T> int getOrder(T obj) {
		Order order = obj.getClass().getAnnotation(Order.class);
		if (order == null) {
			return Order.LOWEST_PRECEDENCE;
		}
		return order.value();
	}

	private static class ServiceContextMap {

		private static final ThreadLocal<WeakReference<Object>> currentKey = new ThreadLocal<>();
		
		private Map<Object, Map<String, Object>> contextMapByKeys = new WeakHashMap<>();
		
		private Set<Class<?>> keyTypes = Collections.newSetFromMap(new ConcurrentHashMap<>());

		public synchronized Map<String, Object> getContextMap() {
			WeakReference<Object> ref = currentKey.get();
			Objects.requireNonNull(ref, "context key not set");

			Object key = ref.get();
			Objects.requireNonNull(key, "context key not available");
			return contextMapByKeys.computeIfAbsent(key, any -> new ConcurrentHashMap<>());
		}

		public boolean hasContext() {
			WeakReference<Object> ref = currentKey.get();
			return ref != null && ref.get() != null;
		}

		public void clear() {
			contextMapByKeys.clear();
			keyTypes.clear();
			currentKey.remove();
		}

		public void createContext(Object key) {
			verifyIdentityEquals(key);
			currentKey.set(new WeakReference<>(key));
		}

		public void createContextIfNeeded(Object key) {
			if (!hasContext()) {
				createContext(key);
			}
		}

		private void verifyIdentityEquals(Object obj) {
			if (keyTypes.contains(obj.getClass())) {
				return;
			}

			if (!obj.getClass().equals(Object.class)) {
				try {
					Method method = obj.getClass().getMethod("equals", Object.class);
					ValidationHelper.ensure(method.getDeclaringClass().equals(Object.class),
							"unexpected declaration class for " + method);

					method = obj.getClass().getMethod("hashCode");
					ValidationHelper.ensure(method.getDeclaringClass().equals(Object.class),
							"unexpected declaration class for " + method);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			keyTypes.add(obj.getClass());
		}
	}
}
