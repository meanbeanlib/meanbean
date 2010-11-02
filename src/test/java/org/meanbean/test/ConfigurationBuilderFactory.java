package org.meanbean.test;

import java.util.HashMap;
import java.util.Map;

import org.meanbean.factories.Factory;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.SimpleRandomNumberGenerator;

public class ConfigurationBuilderFactory {

	public static final int ITERATIONS = 4637;

	public static final String IGNORE_PROPERTY_1 = "TEST_IGNORE_PROPERTY_1";

	public static final String IGNORE_PROPERTY_2 = "TEST_IGNORE_PROPERTY_2";

	public static final String IGNORE_PROPERTY_3 = "TEST_IGNORE_PROPERTY_3";

	public static final String OVERRIDE_PROPERTY_1 = "TEST_IGNORE_PROPERTY_1";

	public static final String OVERRIDE_PROPERTY_2 = "TEST_IGNORE_PROPERTY_2";
	
	private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SimpleRandomNumberGenerator();
	
	public static final LongFactory OVERRIDE_FACTORY_1 = new LongFactory(RANDOM_NUMBER_GENERATOR);

	public static final StringFactory OVERRIDE_FACTORY_2 = new StringFactory(RANDOM_NUMBER_GENERATOR);

	public static ConfigurationBuilder create() {
		String[] ignoreProperties = { IGNORE_PROPERTY_1, IGNORE_PROPERTY_2, IGNORE_PROPERTY_3 };
		Map<String, Factory<?>> overrideFactories = new HashMap<String, Factory<?>>();
		overrideFactories.put(OVERRIDE_PROPERTY_1, OVERRIDE_FACTORY_1);
		overrideFactories.put(OVERRIDE_PROPERTY_2, OVERRIDE_FACTORY_2);
		return create(ITERATIONS, ignoreProperties, overrideFactories);
	}

	public static ConfigurationBuilder create(int iterations, String[] ignoreProperties,
	        Map<String, Factory<?>> overrideFactories) {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.iterations(iterations);
		for (String property : ignoreProperties) {
			configurationBuilder.ignoreProperty(property);
		}
		for (Map.Entry<String, Factory<?>> override : overrideFactories.entrySet()) {
			configurationBuilder.overrideFactory(override.getKey(), override.getValue());
		}
		return configurationBuilder;
	}

}
