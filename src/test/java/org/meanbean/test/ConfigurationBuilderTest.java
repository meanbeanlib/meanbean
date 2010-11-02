package org.meanbean.test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.StringFactory;
import static org.meanbean.test.ConfigurationBuilderFactory.IGNORE_PROPERTY_1;
import static org.meanbean.test.ConfigurationBuilderFactory.IGNORE_PROPERTY_2;
import static org.meanbean.test.ConfigurationBuilderFactory.IGNORE_PROPERTY_3;
import static org.meanbean.test.ConfigurationBuilderFactory.ITERATIONS;

import static org.meanbean.test.ConfigurationBuilderFactory.OVERRIDE_FACTORY_1;
import static org.meanbean.test.ConfigurationBuilderFactory.OVERRIDE_FACTORY_2;
import static org.meanbean.test.ConfigurationBuilderFactory.OVERRIDE_PROPERTY_1;
import static org.meanbean.test.ConfigurationBuilderFactory.OVERRIDE_PROPERTY_2;

public class ConfigurationBuilderTest {

	@Test
	public void iterationsShouldSetIterationsOnConfiguration() throws Exception {
		ConfigurationBuilder configurationBuilder = ConfigurationBuilderFactory.create();
		Configuration configuration = configurationBuilder.build();
		assertThat("Iterations are wrong.", configuration.getIterations(), is(ConfigurationBuilderFactory.ITERATIONS));
	}

	@Test(expected = IllegalArgumentException.class)
	public void iterationsShouldPreventZeroIterations() {
		new ConfigurationBuilder().iterations(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void iterationsShouldPreventNegativeIterations() {
		new ConfigurationBuilder().iterations(-1);
	}

	@Test
	public void ignorePropertyShouldReturnTrueForPropertiesToIgnore() {
		ConfigurationBuilder configurationBuilder = ConfigurationBuilderFactory.create();
		final String testProperty = "AN_ADDITIONAL_TEST_PROPERTY";
		configurationBuilder.ignoreProperty(testProperty);
		Configuration configuration = configurationBuilder.build();
		assertThat("Property should be ignored.",
		        configuration.isIgnoredProperty(IGNORE_PROPERTY_1), is(true));
		assertThat("Property should be ignored.",
		        configuration.isIgnoredProperty(IGNORE_PROPERTY_2), is(true));
		assertThat("Property should be ignored.",
		        configuration.isIgnoredProperty(IGNORE_PROPERTY_3), is(true));
		assertThat("Property should be ignored.", configuration.isIgnoredProperty(testProperty), is(true));
	}

	@Test
	public void ignorePropertyShouldReturnFalseForPropertiesNotToIgnore() {
		ConfigurationBuilder configurationBuilder = ConfigurationBuilderFactory.create();
		Configuration configuration = configurationBuilder.build();
		assertThat("Property should not be ignored.",
		        configuration.isIgnoredProperty(IGNORE_PROPERTY_1 + "_DIFFERENT"),
		        is(false));
		assertThat("Property should not be ignored.",
		        configuration.isIgnoredProperty(IGNORE_PROPERTY_2 + "_DIFFERENT"),
		        is(false));
		assertThat("Property should not be ignored.",
		        configuration.isIgnoredProperty(IGNORE_PROPERTY_3 + "_DIFFERENT"),
		        is(false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void ignorePropertyShouldPreventNullProperty() {
		new ConfigurationBuilder().ignoreProperty(null);
	}

	/*
	 * configurationBuilder.overrideFactory(property, factory); configurationBuilder.build();
	 */

	@Test(expected = IllegalArgumentException.class)
	public void overrideFactoryShouldPreventNullProperty() throws Exception {
		new ConfigurationBuilder().overrideFactory(null, OVERRIDE_FACTORY_1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void overrideFactoryShouldPreventNullFactory() throws Exception {
		new ConfigurationBuilder().overrideFactory(OVERRIDE_PROPERTY_1, null);
	}

	@Test
	public void overrideFactoryShouldSetOverrideFactoryInConfiguration() throws Exception {
		ConfigurationBuilder configurationBuilder = ConfigurationBuilderFactory.create();
		Configuration configuration = configurationBuilder.build();
		assertThat("Override Factory not set.",
		        (LongFactory) configuration.getOverrideFactory(OVERRIDE_PROPERTY_1),
		        is(OVERRIDE_FACTORY_1));
		assertThat("Override Factory not set.",
		        (StringFactory) configuration.getOverrideFactory(OVERRIDE_PROPERTY_2),
		        is(OVERRIDE_FACTORY_2));
	}

	@Test
	public void overrideFactoryShouldOnlySetSpecifiedOverrideFactoriesInConfiguration() throws Exception {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		Configuration configuration = configurationBuilder.build();
		assertThat("Override Factory not set.",
		        configuration.getOverrideFactory(OVERRIDE_PROPERTY_1), is(nullValue()));
		assertThat("Override Factory not set.",
		        configuration.getOverrideFactory(OVERRIDE_PROPERTY_2), is(nullValue()));
	}

	@Test
	public void buildShouldConstructConfiguration() throws Exception {

	}

	@Test
	public void testToString() throws Exception {
		ConfigurationBuilder configurationBuilder = ConfigurationBuilderFactory.create();
		StringBuilder expected = new StringBuilder();
		expected.append("ConfigurationBuilder[");
		expected.append("iterations=");
		expected.append(ITERATIONS).append(",");
		expected.append("ignoredProperties=[");
		expected.append(IGNORE_PROPERTY_1).append(", ");
		expected.append(IGNORE_PROPERTY_2).append(", ");
		expected.append(IGNORE_PROPERTY_3);
		expected.append("],");
		expected.append("overrideFactories={");
		expected.append(OVERRIDE_PROPERTY_1).append("=").append(OVERRIDE_FACTORY_1).append(", ");
		expected.append(OVERRIDE_PROPERTY_2).append("=").append(OVERRIDE_FACTORY_2);
		expected.append("}]");
		assertThat("Invalid toString.", configurationBuilder.toString(), is(expected.toString()));
	}
}