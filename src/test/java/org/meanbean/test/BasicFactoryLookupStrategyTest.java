package org.meanbean.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.bean.factory.DynamicBeanFactory;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.basic.EnumFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;

public class BasicFactoryLookupStrategyTest {

	private static final String PROPERTY_NAME = "A PROPERTY";

	private static final String IRRELEVANT_PROPERTY_NAME = "IRRELEVANT";

	private RandomValueGenerator randomValueGenerator;

	private FactoryRepository factoryCollection;

	private BasicFactoryLookupStrategy factoryLookupStrategy;

	@Before
	public void before() {
		randomValueGenerator = new SimpleRandomValueGenerator();
		factoryCollection = new FactoryRepository(randomValueGenerator);
		factoryLookupStrategy = new BasicFactoryLookupStrategy(factoryCollection, randomValueGenerator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullFactoryCollection() throws Exception {
		new BasicFactoryLookupStrategy(null, randomValueGenerator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullRandomValueGenerator() throws Exception {
		new BasicFactoryLookupStrategy(factoryCollection, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFactoryShouldPreventNullPropertyName() throws Exception {
		factoryLookupStrategy.getFactory(null, String.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFactoryShouldPreventNullPropertyType() throws Exception {
		factoryLookupStrategy.getFactory(IRRELEVANT_PROPERTY_NAME, null, null);
	}

	@Test
	public void getFactoryShouldReturnRegisteredFactoryForRegisteredTypes() throws Exception {
		Factory<?> factory = factoryLookupStrategy.getFactory(IRRELEVANT_PROPERTY_NAME, String.class, null);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(StringFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnEnumFactoryForEnumTypes() throws Exception {
		Factory<?> factory = factoryLookupStrategy.getFactory(IRRELEVANT_PROPERTY_NAME, Color.class, null);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(EnumFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnDynamicBeanFactoryForUnrecognisedBeanTypes() throws Exception {
		Factory<?> factory = factoryLookupStrategy.getFactory(IRRELEVANT_PROPERTY_NAME, BasicBean.class, null);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(DynamicBeanFactory.class.getName()));
	}

	@Test(expected = NoSuchFactoryException.class)
	public void getFactoryShouldThrowNoSuchFactoryExceptionForUnsupportedTypes() throws Exception {
		factoryLookupStrategy.getFactory(IRRELEVANT_PROPERTY_NAME, NonBean.class, null);
	}

	@Test
	public void getFactoryShouldReturnFactoryInConfigurationRatherThanRegisteredFactory() throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory(PROPERTY_NAME, new CustomFactory())
		        .build();
		Factory<?> factory = factoryLookupStrategy.getFactory(PROPERTY_NAME, String.class, configuration);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(CustomFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnFactoryInConfigurationRatherThanEnumFactory() throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory(PROPERTY_NAME, new CustomFactory())
		        .build();
		Factory<?> factory = factoryLookupStrategy.getFactory(PROPERTY_NAME, Color.class, configuration);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(CustomFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnFactoryInConfigurationRatherThanDynamicBeanFactory() throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory(PROPERTY_NAME, new CustomFactory())
		        .build();
		Factory<?> factory = factoryLookupStrategy.getFactory(PROPERTY_NAME, BasicBean.class, configuration);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(CustomFactory.class.getName()));
	}

	public enum Color {
		RED, BLUE, GREEN
	}

	public static class BasicBean {
	}

	public static class NonBean {
		public NonBean(String parameter) {
			// Do nothing
		}
	}

	private static class CustomFactory implements Factory<Object> {
		@Override
		public Object create() {
			return null; // No used
		}
	}
}