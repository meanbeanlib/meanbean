package org.meanbean.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads service through META-INF/services mechanism, additionally providing caching and ordering behavior
 */
public class ServiceFactory<T> {

	private static final Map<String, Object> factoryCache = new ConcurrentHashMap<>();

	private final List<T> services;

	@SuppressWarnings("unchecked")
	static synchronized <T> ServiceFactory<T> getInstance(ServiceDefinition<T> definition) {
		String inprogressKey = "Load of " + definition.getServiceType().getName() + " already in progress";

		if (factoryCache.containsKey(inprogressKey)) {
			throw new IllegalStateException(inprogressKey);
		}

		factoryCache.put(inprogressKey, inprogressKey);
		try {
			return (ServiceFactory<T>) factoryCache.computeIfAbsent(definition.getServiceType().getName(),
					key -> ServiceFactory.create(definition));
		} finally {
			factoryCache.remove(inprogressKey);
		}
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
