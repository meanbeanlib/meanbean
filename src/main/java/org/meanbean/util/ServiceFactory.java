package org.meanbean.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Loads service through META-INF/services mechanism, additionally providing caching and ordering behavior
 */
public class ServiceFactory<T> {

	private static final Map<Class<?>, ServiceFactory<?>> factoryCache = new ConcurrentHashMap<>();

	private Class<T> serviceType;
	private Object[] constructorArgs = {};
	private Class<?>[] constructorTypes = {};
	
	private List<T> services;

	@SuppressWarnings("unchecked")
	public static <T> ServiceFactory<T> getInstance(Class<T> serviceType) {
		ServiceFactory<?> factory = factoryCache.computeIfAbsent(serviceType, key -> newInstance(serviceType));
		return (ServiceFactory<T>) factory;
	}

	public static <T> ServiceFactory<T> newInstance(Class<T> serviceType) {
		return new ServiceFactory<T>(serviceType);
	}
	
	public static void clearCache() {
		factoryCache.clear();
	}

	private ServiceFactory(Class<T> serviceType) {
		this.serviceType = serviceType;
	}

	public ServiceFactory<T> constructorArgs(Object... args) {
		this.constructorArgs = args;
		this.constructorTypes = Stream.of(args)
				.map(Object::getClass)
				.toArray(Class<?>[]::new);
		return this;
	}

	public ServiceFactory<T> constructorTypes(Class<?>... types) {
		this.constructorTypes = types;
		return this;
	}

	public T loadFirst() {
		List<T> services = load();
		if (services.isEmpty()) {
			throw new IllegalArgumentException("cannot find services for " + serviceType);
		}
		return services.get(0);
	}

	public List<T> load() {
		if (services != null) {
			return services;
		}
		
		ServiceLoader<T> loader = new ServiceLoader<>(serviceType, constructorTypes);
		services = loader.createAll(constructorArgs);
		Collections.sort(services, Comparator.comparingInt(this::getOrder));
		return services;
	}

	private int getOrder(T obj) {
		Order order = obj.getClass().getAnnotation(Order.class);
		if (order == null) {
			return Order.LOWEST_PRECEDENCE;
		}
		return order.value();
	}
}
