package org.meanbean.util;

import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Alternative service loader that supports constructors with arguments in opposite to {@link java.util.ServiceLoader}.
 *
 * @param <T>
 *            Service interface
 */
public final class ServiceLoader<T> {

	private static final $Logger logger = $LoggerFactory.getLogger(ServiceLoader.class);
	
	private static final String SERVICE_PREFIX = "META-INF/services/";

	private final Class<? extends T> service;
	private final Class<?>[] argumentTypes;

	private final ClassLoader classLoader;
	private final Collection<String> classes;

	/**
	 * @param service
	 *            Service interface
	 * @param argumentTypes
	 *            Expected argument types for constructors
	 */
	public ServiceLoader(final Class<? extends T> service, final Class<?>... argumentTypes) {
		this.service = service;
		this.argumentTypes = argumentTypes;
		this.classLoader = Thread.currentThread().getContextClassLoader() != null
				? Thread.currentThread().getContextClassLoader()
				: getClass().getClassLoader();
		this.classes = loadClasses(classLoader, service);
	}

	/**
	 * Creates all registered service implementations.
	 *
	 * @param arguments
	 *            Arguments for constructors of service implementations
	 * @return Instances of all service implementations
	 */
	public List<T> createAll(final Object... arguments) {
		List<T> instances = new ArrayList<T>(classes.size());

		for (String className : classes) {
			T instance = createInstance(className, arguments);
			if (instance != null) {
				instances.add(instance);
			}
		}

		return instances;
	}

	/**
	 * Loads all registered service class names.
	 *
	 * @param <T>
	 *            Service interface
	 * @param classLoader
	 *             Class loader to use for finding service files
	 * @param service
	 *            Service interface
	 * @return Class names
	 */
	private static <T> Collection<String> loadClasses(final ClassLoader classLoader, final Class<? extends T> service) {
		String name = SERVICE_PREFIX + service.getName();
		Enumeration<URL> urls;
		try {
			urls = classLoader.getResources(name);
		} catch (IOException ex) {
			logger.error("Failed loading services from '{}'", name);
			return Collections.emptyList();
		}

		Collection<String> classes = new ArrayList<String>();

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					line = line.trim();
					if (line.length() > 0 && line.charAt(0) != '#' && !classes.contains(line)) {
						classes.add(line);
					}
				}
			} catch (IOException ex) {
				logger.error("Failed reading service resource '{}'", url, ex);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
						// Ignore
					}
				}
			}
		}

		return classes;
	}

	/**
	 * Creates a new instance of a class.
	 *
	 * @param className
	 *            Fully-qualified class name
	 * @param arguments
	 *            Arguments for constructor
	 * @return A new instance of given class or {@code null} if creation failed
	 */
	private T createInstance(final String className, final Object... arguments) {
		try {
			Class<?> implementation = Class.forName(className, false, classLoader);
			Constructor<?> constructor = implementation.getDeclaredConstructor(argumentTypes);
			Object newInstance = constructor.newInstance(arguments);
			return this.service.cast(newInstance);
		} catch (Exception ex) {
			throw new IllegalStateException("Cannot create service instance for " + className, ex);
		}
	}

}
