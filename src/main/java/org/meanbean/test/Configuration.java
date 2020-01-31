package org.meanbean.test;

import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Type-specific (i.e. JavaBean-specific) test configuration used to customize testing for a single type. Configuration
 * objects are only required when you want to customize the testing behaviour. Most often the standard testing behaviour
 * will be sufficient. <br/>
 * </p>
 * 
 * <p>
 * Configuration objects should be created using a <code>ConfigurationBuilder</code>. <br/>
 * </p>
 * 
 * <p>
 * Configuration objects can be passed to <code>BeanTester.test(Class<?>,Configuration);</code> for use as a one-off, or
 * registered with the BeanTester for use thereafter:
 * <code>BeanTester.addCustomConfiguration(Class<?>,Configuration);</code>. <br/>
 * </p>
 * 
 * <p>
 * The following can be configured/customized:
 * </p>
 * 
 * <ul>
 * <li>The number of times a type is tested.</li>
 * <li>Whether a property is tested or not, by specifying properties of a type that should be ignored.</li>
 * <li>The Factory that should be used when generating test data for a given property.</li>
 * </ul>
 * 
 * @author Graham Williamson
 */
public class Configuration {

	/** The number of times a type should be tested. This will be null if it has not been overriden. */
	private final Integer iterations;

	/** Any properties of a type that should not be tested. Contains property names. */
	private final Set<String> ignoredProperties;

	/**
	 * Factories that should be used for specific properties, overriding standard Factory selection. Keyed by property
	 * name.
	 */
	private final Map<String, Factory<?>> overrideFactories;

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper();

	/**
	 * Construct a new Configuration.
	 * 
	 * @param iterations
	 *            The number of times a type should be tested.
	 * @param ignoredProperties
	 *            Any properties of a type that should not be tested. Contains property names.
	 * @param overrideFactories
	 *            Factories that should be used for specific properties, overriding standard Factory selection.
	 */
	Configuration(Integer iterations, Set<String> ignoredProperties, Map<String, Factory<?>> overrideFactories) {
		this.iterations = iterations;
		this.ignoredProperties = Collections.unmodifiableSet(ignoredProperties);
		this.overrideFactories = Collections.unmodifiableMap(overrideFactories);
	}

	/**
	 * Does this Configuration contain an override for the number of times a type should be tested, or should the
	 * standard global configuration setting be used instead?
	 * 
	 * @return <code>true</code> if this Configuration contains an override for the number of times a type should be
	 *         tested; <code>false</code> if the standard global configuration setting should be used instead.
	 */
	public boolean hasIterationsOverride() {
		return getIterations() != null;
	}

	/**
	 * Get the number of times a type should be tested.
	 * 
	 * @return The number of times a type should be tested. This will be <code>null</code> if the number of times a type
	 *         should be tested has not been overridden in this Configuration. Use <code>hasIterationsOverride()</code>
	 *         first to check whether an iterations override is present.
	 */
	public Integer getIterations() {
		return iterations;
	}

	/**
	 * Should the specified property been disregarded/ignored during testing?
	 * 
	 * @param property
	 *            The name of the property.
	 * 
	 * @return <code>true</code> if the property should be disregarded/ignored during testing; <code>false</code>
	 *         otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the property parameter is deemed illegal. For example, if it is null.
	 */
	public boolean isIgnoredProperty(String property) throws IllegalArgumentException {
		validationHelper.ensureExists("property", "check whether a property is ignored", property);
		return ignoredProperties.contains(property);
	}

	/**
	 * <p>
	 * Does the specified property have an override Factory?
	 * </p>
	 * 
	 * <p>
	 * That is, has a Factory been registered within this Configuration as an override to standard Factory selection for
	 * the specified property?
	 * </p>
	 * 
	 * @param property
	 *            The name of the property.
	 * 
	 * @return <code>true</code> if the property does have an override Factory; <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the property parameter is deemed illegal. For example, if it is null.
	 */
	public boolean hasOverrideFactory(String property) throws IllegalArgumentException {
		validationHelper.ensureExists("property", "check whether a property has an override Factory", property);
		return overrideFactories.containsKey(property);
	}

	/**
	 * <p>
	 * Get the override Factory for the specified property, if one has been registered in this Configuration.
	 * </p>
	 * 
	 * <p>
	 * The returned Factory will be used over the standard Factory for the property.
	 * </p>
	 * 
	 * @param property
	 *            The name of the property.
	 * 
	 * @return The specified property's override Factory, if one has been registered in this Configuration. Otherwise,
	 *         <code>null</code>.
	 * 
	 * @throws IllegalArgumentException
	 *             If the property parameter is deemed illegal. For example, if it is null.
	 */
	public Factory<? extends Object> getOverrideFactory(String property) throws IllegalArgumentException {
		validationHelper.ensureExists("property", "get override Factory", property);
		return overrideFactories.get(property);
	}

	/**
	 * Get a human-readable String representation of this object.
	 * 
	 * @return A human-readable String representation of this object.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Configuration[");
		str.append("iterations=").append(iterations).append(",");
		str.append("ignoredProperties=").append(ignoredProperties).append(",");
		str.append("overrideFactories=").append(overrideFactories);
		str.append("]");
		return str.toString();
	}
}