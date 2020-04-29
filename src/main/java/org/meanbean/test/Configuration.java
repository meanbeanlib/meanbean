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

package org.meanbean.test;

import org.meanbean.lang.Factory;
import org.meanbean.util.ValidationHelper;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.EnumSet.noneOf;

/**
 * <p>
 * Test configuration used to customize testing for a single JavaBean type. Configuration
 * objects are only required when you want to customize the testing behaviour. Most often the standard testing behaviour
 * will be sufficient. <br>
 * </p>
 * 
 * <p>
 * Configuration objects should be created using a <code>ConfigurationBuilder</code>. <br>
 * </p>
 * 
 * <p>
 * Configuration objects can be passed to <code>BeanTester.test(Class&lt;?&gt;,Configuration);</code> for use as a one-off, or
 * registered with the BeanTester for use thereafter:
 * <code>BeanTester.addCustomConfiguration(Class&lt;?&gt;,Configuration);</code>. <br>
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
 * <br>
 * <b>Prefer {@link BeanTesterBuilder}</b>
 * <br>
 * @author Graham Williamson
 */
public class Configuration {

	/** The number of times a type should be tested. This will be null if it has not been overriden. */
	private Integer iterations;

	/** Any properties of a type that should not be tested. Contains property names. */
	private Set<String> ignoredProperties;

	/**
	 * Factories that should be used for specific properties, overriding standard Factory selection. Keyed by property
	 * name.
	 */
	private Map<String, Factory<?>> overrideFactories;

	private List<String> equalsInsignificantProperties = new ArrayList<>();
	
	private Set<Warning> suppressedWarnings = EnumSet.noneOf(Warning.class);
	
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
    Configuration(Integer iterations, Set<String> ignoredProperties, Map<String, Factory<?>> overrideFactories,
            Set<Warning> suppressedWarnings) {
        this.iterations = iterations;
		this.ignoredProperties = ignoredProperties;
		this.overrideFactories = overrideFactories;
		this.suppressedWarnings = suppressedWarnings;
	}

    static Configuration defaultConfiguration() {
        return new Configuration(BeanTester.TEST_ITERATIONS_PER_BEAN, emptySet(), emptyMap(), noneOf(Warning.class));
    }

    static Configuration defaultMutableConfiguration(int iterations) {
        return new Configuration(iterations, new HashSet<>(), new HashMap<>(), EnumSet.noneOf(Warning.class));
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
		ValidationHelper.ensureExists("property", "check whether a property is ignored", property);
		return ignoredProperties.contains(property);
	}
	
	public boolean isSuppressedWarning(Warning warning) {
	    return suppressedWarnings.contains(warning);
	}

    void suppress(Warning warning) {
        suppressedWarnings.add(warning);
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
		ValidationHelper.ensureExists("property", "check whether a property has an override Factory", property);
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
		ValidationHelper.ensureExists("property", "get override Factory", property);
		return overrideFactories.get(property);
	}

	Set<String> getIgnoredProperties() {
		return ignoredProperties;
	}

	void setIgnoredProperties(Set<String> ignoredProperties) {
		this.ignoredProperties = ignoredProperties;
	}

	Map<String, Factory<?>> getOverrideFactories() {
		return overrideFactories;
	}

	void setOverrideFactories(Map<String, Factory<?>> overrideFactories) {
		this.overrideFactories = overrideFactories;
	}

	void setIterations(Integer iterations) {
		ValidationHelper.ensure(iterations >= 1, "Iterations must be at least 1.");
		this.iterations = iterations;
	}

	List<String> getEqualsInsignificantProperties() {
		return equalsInsignificantProperties;
	}

	void setEqualsInsignificantProperties(List<String> equalsInsignificantProperties) {
		this.equalsInsignificantProperties = equalsInsignificantProperties;
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
