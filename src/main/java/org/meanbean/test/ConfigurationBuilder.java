package org.meanbean.test;

import org.meanbean.lang.Factory;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Builder object that makes creating Configuration objects easier.
 * 
 * @author Graham Williamson
 */
public class ConfigurationBuilder {

    /** Logging mechanism. */
    private static final $Logger logger = $LoggerFactory.getLogger(ConfigurationBuilder.class);

	/** The number of times a type should be tested. */
	private Integer iterations;

	/** Any properties of a type that should not be tested. Contains property names. */
	private final Set<String> ignoredProperties = ConcurrentHashMap.newKeySet();

	/**
	 * Factories that should be used for specific properties, overriding standard Factory selection. Keyed by property
	 * name.
	 */
	private final Map<String, Factory<?>> overrideFactories = new ConcurrentHashMap<>();

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(logger);

	/**
	 * Construct a new Configuration Builder.
	 */
	public ConfigurationBuilder() {
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
		if (iterations < 1) {
			throw new IllegalArgumentException("Iterations must be at least 1.");
		}
		this.iterations = iterations;
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
		validationHelper.ensureExists("property", "add property to ignored properties collection", property);
		ignoredProperties.add(property);
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
		validationHelper.ensureExists("property", "add override Factory", property);
		validationHelper.ensureExists("factory", "add override Factory", factory);
		overrideFactories.put(property, factory);
		return this;
	}

	/**
	 * Build a Configuration.
	 * 
	 * @return A Configuration object.
	 */
	public Configuration build() {
		Configuration configuration =
		        new Configuration(iterations, Collections.unmodifiableSet(ignoredProperties),
		                Collections.unmodifiableMap(overrideFactories));
		Configuration result = configuration;
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