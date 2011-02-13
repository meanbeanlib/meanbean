package org.meanbean.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Builder object that makes creating Configuration objects easier.
 * 
 * @author Graham Williamson
 */
public class ConfigurationBuilder {

	/** The number of times a type should be tested. */
	private Integer iterations;

	/** Any properties of a type that should not be tested. Contains property names. */
	private final Set<String> ignoredProperties = Collections.synchronizedSet(new HashSet<String>());

	/**
	 * Factories that should be used for specific properties, overriding standard Factory selection. Keyed by property
	 * name.
	 */
	private final Map<String, Factory<?>> overrideFactories = Collections
	        .synchronizedMap(new HashMap<String, Factory<?>>());

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(ConfigurationBuilder.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/**
	 * Construct a new Configuration Builder.
	 */
	public ConfigurationBuilder() {
		log.debug("ConfigurationBuilder: entering.");
		log.debug("ConfigurationBuilder: exiting.");
	}

	/**
	 * Set the number of times a type should be tested.
	 * 
	 * @param iterations
	 *            The number of times a type should be tested.
	 * 
	 * @throws IllegalArgumentException
	 *             If the iterations parameter is deemed illegal. For example, if it is less than 1.
	 * 
	 * @return A Configuration Builder.
	 */
	public ConfigurationBuilder iterations(int iterations) {
		log.debug("iterations: entering with iterations=[" + iterations + "].");
		if (iterations < 1) {
			log.debug("iterations: Iterations must be at least 1. Throw IllegalArgumentException.");
			throw new IllegalArgumentException("Iterations must be at least 1.");
		}
		this.iterations = iterations;
		log.debug("iterations: exiting returning [" + this + "].");
		return this;
	}

	/**
	 * Mark the specified property as one to be disregarded/ignored during testing.
	 * 
	 * @param property
	 *            The name of the property.
	 * 
	 * @throws IllegalArgumentException
	 *             If the property parameter is deemed illegal. For example, if it is null.
	 * 
	 * @return A Configuration Builder.
	 */
	public ConfigurationBuilder ignoreProperty(String property) throws IllegalArgumentException {
		log.debug("ignoreProperty: entering with property=[" + property + "].");
		validationHelper.ensureExists("property", "add property to ignored properties collection", property);
		ignoredProperties.add(property);
		log.debug("ignoreProperty: exiting returning [" + this + "].");
		return this;
	}

	/**
	 * Register the specified Factory as an override Factory for the specified property. This means that the specified
	 * Factory will be used over the standard Factory for the property.
	 * 
	 * @param property
	 *            The name of the property.
	 * @param factory
	 *            The Factory to use to override standard Factory selection for the specified property.
	 * 
	 * @throws IllegalArgumentException
	 *             If either the property or factory parameter is deemed illegal. For example, if either is null.
	 * 
	 * @return A Configuration Builder.
	 */
	public ConfigurationBuilder overrideFactory(String property, Factory<?> factory) throws IllegalArgumentException {
		log.debug("overrideFactory: entering with property=[" + property + "], factory=[" + factory + "].");
		validationHelper.ensureExists("property", "add override Factory", property);
		validationHelper.ensureExists("factory", "add override Factory", factory);
		overrideFactories.put(property, factory);
		log.debug("overrideFactory: exiting returning[" + this + "].");
		return this;
	}

	/**
	 * Build a Configuration.
	 * 
	 * @return A Configuration object.
	 */
	public Configuration build() {
		log.debug("build: entering.");
		Configuration configuration =
		        new Configuration(iterations, Collections.unmodifiableSet(ignoredProperties),
		                Collections.unmodifiableMap(overrideFactories));
		Configuration result = configuration;
		log.debug("build: exiting returning [" + result + "].");
		return result;
	}

	/**
	 * Get a human-readable String representation of this object.
	 * 
	 * @return A human-readable String representation of this object.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("ConfigurationBuilder[");
		str.append("iterations=").append(iterations).append(",");
		str.append("ignoredProperties=").append(new TreeSet<String>(this.ignoredProperties)).append(",");
		str.append("overrideFactories=").append(new TreeMap<String, Factory<?>>(this.overrideFactories));
		str.append("]");
		return str.toString();
	}
}