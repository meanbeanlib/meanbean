package org.meanbean.util;

import java.util.Arrays;

/**
 * Defines how service implementations should be instantiated
 */
public class ServiceDefinition<T> {

	private Class<T> serviceType;
	private Class<?>[] constructorTypes = {};
	private Object[] constructorArgs = {};

	public ServiceDefinition(Class<T> serviceType) {
		this(serviceType, new Class<?>[] {}, new Object[] {});
	}

	public ServiceDefinition(Class<T> serviceType, Class<?>[] constructorTypes, Object[] constructorArgs) {
		if (constructorTypes.length != constructorArgs.length) {
			throw new IllegalArgumentException();
		}
		this.serviceType = serviceType;
		this.constructorTypes = Arrays.copyOf(constructorTypes, constructorTypes.length);
		this.constructorArgs = Arrays.copyOf(constructorArgs, constructorTypes.length);
	}

	Class<T> getServiceType() {
		return serviceType;
	}

	Class<?>[] getConstructorTypes() {
		return constructorTypes;
	}

	Object[] getConstructorArgs() {
		return constructorArgs;
	}

	public ServiceFactory<T> getServiceFactory(){
		return ServiceFactory.getInstance(this);
	}
}
