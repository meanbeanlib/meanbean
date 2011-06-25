package org.meanbean.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;
import org.meanbean.factories.basic.DateFactory;
import org.meanbean.factories.basic.IntegerFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;

public class ConfigurationTest {

	private static final Integer VALID_ITERATIONS = 5735;

	private static final Integer NULL_ITERATIONS = null;

	private static final Set<String> EMPTY_IGNORED_PROPERTIES = Collections.emptySet();

	private static final Set<String> POPULATED_IGNORED_PROPERTIES = new TreeSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("firstName");
			add("surname");
			add("telephoneNumber");
		}
	};

	private static final RandomValueGenerator RANDOM_NUMBER_GENERATOR = new SimpleRandomValueGenerator();

	private static final Map<String, Factory<?>> EMPTY_OVERRIDE_FACTORIES = Collections.emptyMap();

	private static final Map<String, Factory<?>> POPULATED_OVERRIDE_FACTORIES = new TreeMap<String, Factory<?>>() {
		private static final long serialVersionUID = 1L;
		{
			put("middleName", new StringFactory(RANDOM_NUMBER_GENERATOR));
			put("dateOfBirth", new DateFactory(RANDOM_NUMBER_GENERATOR));
			put("numberOfDependents", new IntegerFactory(RANDOM_NUMBER_GENERATOR));
		}
	};

	@Test
	public void configurationConstructedWithNonNullIterationsShouldHaveIterationsOverride() throws Exception {
		Configuration configuration =
		        new Configuration(VALID_ITERATIONS, POPULATED_IGNORED_PROPERTIES, POPULATED_OVERRIDE_FACTORIES);
		assertThat("Incorrect iterations.", configuration.getIterations(), is(VALID_ITERATIONS));
		assertThat("Should not have iterations override.", configuration.hasIterationsOverride(), is(true));
	}

	@Test
	public void configurationConstructedWithNullIterationsShouldNotHaveIterationsOverride() throws Exception {
		Configuration configuration =
		        new Configuration(NULL_ITERATIONS, POPULATED_IGNORED_PROPERTIES, POPULATED_OVERRIDE_FACTORIES);
		assertThat("Incorrect iterations.", configuration.getIterations(), is(NULL_ITERATIONS));
		assertThat("Should not have iterations override.", configuration.hasIterationsOverride(), is(false));
	}

	@Test
	public void configurationConstructedWithNonEmptyPropertiesShouldHaveIgnoredProperties() throws Exception {
		Configuration configuration =
		        new Configuration(VALID_ITERATIONS, POPULATED_IGNORED_PROPERTIES, POPULATED_OVERRIDE_FACTORIES);
		for (String property : POPULATED_IGNORED_PROPERTIES) {
			assertThat("Property [" + property + "] should be ignored.", configuration.isIgnoredProperty(property),
			        is(true));
		}
	}

	@Test
	public void configurationConstructedWithEmptyPropertiesShouldNotHaveIgnoredProperties() throws Exception {
		Configuration configuration =
		        new Configuration(VALID_ITERATIONS, EMPTY_IGNORED_PROPERTIES, POPULATED_OVERRIDE_FACTORIES);
		for (String property : POPULATED_IGNORED_PROPERTIES) {
			assertThat("Property [" + property + "] should not be ignored.", configuration.isIgnoredProperty(property),
			        is(false));
		}
	}

	@Test
	public void configurationConstructedWithEmptyOverideFactoriesShouldNotHaveOverrideFactories() throws Exception {
		Configuration configuration =
		        new Configuration(VALID_ITERATIONS, POPULATED_IGNORED_PROPERTIES, EMPTY_OVERRIDE_FACTORIES);
		for (Map.Entry<String, Factory<? extends Object>> propertyToFactoryEntry : POPULATED_OVERRIDE_FACTORIES
		        .entrySet()) {
			String property = propertyToFactoryEntry.getKey();
			assertThat("Property [" + property + "] should not have override factory.",
			        configuration.hasOverrideFactory(property), is(false));
			assertThat("Property [" + property + "] should not have override factory.",
			        configuration.getOverrideFactory(property), is(nullValue()));
		}
	}

	@Test
	public void configurationConstructedWithNonEmptyOverideFactoriesShouldHaveOverrideFactories() throws Exception {
		Configuration configuration =
		        new Configuration(VALID_ITERATIONS, POPULATED_IGNORED_PROPERTIES, POPULATED_OVERRIDE_FACTORIES);
		for (Map.Entry<String, Factory<? extends Object>> propertyToFactoryEntry : POPULATED_OVERRIDE_FACTORIES
		        .entrySet()) {
			String property = propertyToFactoryEntry.getKey();
			Factory<? extends Object> factory = propertyToFactoryEntry.getValue();
			assertThat("Property [" + property + "] should not have override factory.",
			        configuration.hasOverrideFactory(property), is(true));
			assertEquals("Property [" + property + "] should not have override factory.",
			        configuration.getOverrideFactory(property), factory);
		}
	}

	@Test
	public void testToString() throws Exception {
		Configuration configuration =
		        new Configuration(VALID_ITERATIONS, POPULATED_IGNORED_PROPERTIES, POPULATED_OVERRIDE_FACTORIES);
		StringBuilder expectedStringBuilder = new StringBuilder();
		expectedStringBuilder.append("Configuration[");
		expectedStringBuilder.append("iterations=").append(VALID_ITERATIONS).append(",");
		expectedStringBuilder.append("ignoredProperties=").append(POPULATED_IGNORED_PROPERTIES).append(",");
		expectedStringBuilder.append("overrideFactories=").append(POPULATED_OVERRIDE_FACTORIES).append("]");
		assertThat("Incorrect toString.", configuration.toString(), is(expectedStringBuilder.toString()));
	}
}