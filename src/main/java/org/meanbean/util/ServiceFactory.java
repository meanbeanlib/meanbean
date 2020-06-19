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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Loads service through META-INF/services mechanism, additionally providing caching and ordering behavior
 */
public class ServiceFactory<T> {

    private static final ThreadLocal<Map<String, Object>> factoryCache = ThreadLocal.withInitial(ConcurrentHashMap::new);
    
    @SuppressWarnings("unused")
	private static final ThreadLocal<AtomicInteger> threadScope = ThreadLocal.withInitial(AtomicInteger::new);

    private final List<T> services;

    @SuppressWarnings("unchecked")
    static synchronized <T> ServiceFactory<T> getInstance(ServiceDefinition<T> definition) {
        String inprogressKey = "Load of " + definition.getServiceType().getName() + " already in progress";

        if (factoryCache().containsKey(inprogressKey)) {
            throw new IllegalStateException(inprogressKey);
        }

        factoryCache().put(inprogressKey, inprogressKey);
        try {
            return (ServiceFactory<T>) factoryCache().computeIfAbsent(
            		definition.getServiceType().getName(),
                    key -> ServiceFactory.create(definition));
        } finally {
            factoryCache().remove(inprogressKey);
        }
    }

    public static void inScope(Runnable runnable) {
    	// disabled for now. see issue #8 and test for ArrayPropertyBeanWithConstructor
//        threadScope.get().incrementAndGet();
        try {
            runnable.run();
        } finally {
//            int scope = threadScope.get().decrementAndGet();
//            if (scope == 0) {
//                factoryCache.remove();
//            }
        }
    }
    
    public static void remove() {
    	factoryCache.remove();
    }

    private static Map<String, Object> factoryCache() {
        return factoryCache.get();
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

}
