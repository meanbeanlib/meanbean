package org.meanbean.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.BasicNewObjectInstanceFactory;
import org.meanbean.factories.FactoryRepository;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.basic.EnumFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.factories.util.BasicFactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NullFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BasicFactoryLookupStrategyTest {

	private static final String PROPERTY_NAME = "A PROPERTY";

	private static final String IRRELEVANT_PROPERTY_NAME = "IRRELEVANT";

	private RandomValueGenerator randomValueGenerator;

	private FactoryRepository factoryCollection;

	private BasicFactoryLookupStrategy factoryLookupStrategy;

	@Mock
	private BeanInformation beanInformationMock;

	private final BeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	private final BeanInformation beanInformationReal = beanInformationFactory.create(BasicBean.class);

	@Before
	public void before() {
		randomValueGenerator = new SimpleRandomValueGenerator();
		factoryCollection = new FactoryRepository();
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
	public void getFactoryShouldPreventNullBeanInformation() throws Exception {
		factoryLookupStrategy.getFactory(null, IRRELEVANT_PROPERTY_NAME, String.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFactoryShouldPreventNullPropertyName() throws Exception {
		factoryLookupStrategy.getFactory(beanInformationMock, null, String.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFactoryShouldPreventNullPropertyType() throws Exception {
		factoryLookupStrategy.getFactory(beanInformationMock, IRRELEVANT_PROPERTY_NAME, null, null);
	}

	@Test
	public void getFactoryShouldReturnRegisteredFactoryForRegisteredTypes() throws Exception {
		Factory<?> factory =
		        factoryLookupStrategy.getFactory(beanInformationMock, IRRELEVANT_PROPERTY_NAME, String.class, null);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(StringFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnEnumFactoryForEnumTypes() throws Exception {
		Factory<?> factory =
		        factoryLookupStrategy.getFactory(beanInformationMock, IRRELEVANT_PROPERTY_NAME, Color.class, null);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(EnumFactory.class.getName()));
	}

	// TODO TEST EQUIVALENT POPULATED BEAN FACTORY

	@Test
	public void getFactoryShouldReturnBasicNewObjectInstanceFactoryForUnrecognisedPropertyTypeThatIsSameAsParentBean()
	        throws Exception {
		Factory<?> factory =
		        factoryLookupStrategy.getFactory(beanInformationReal, IRRELEVANT_PROPERTY_NAME, BasicBean.class, null);
		assertThat("Incorrect factory.", factory.getClass().getName(),
		        is(BasicNewObjectInstanceFactory.class.getName()));
	}

	@Test(expected = NoSuchFactoryException.class)
	public void getFactoryShouldThrowNoSuchFactoryExceptionForUnsupportedTypes() throws Exception {
		factoryLookupStrategy.getFactory(beanInformationReal, IRRELEVANT_PROPERTY_NAME, NonBean.class, null);
	}

	@Test
	public void getFactoryShouldReturnFactoryInConfigurationRatherThanRegisteredFactory() throws Exception {
		Configuration configuration =
		        new ConfigurationBuilder().overrideFactory(PROPERTY_NAME, new NullFactory()).build();
		Factory<?> factory =
		        factoryLookupStrategy.getFactory(beanInformationMock, PROPERTY_NAME, String.class, configuration);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(NullFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnFactoryInConfigurationRatherThanEnumFactory() throws Exception {
		Configuration configuration =
		        new ConfigurationBuilder().overrideFactory(PROPERTY_NAME, new NullFactory()).build();
		Factory<?> factory =
		        factoryLookupStrategy.getFactory(beanInformationMock, PROPERTY_NAME, Color.class, configuration);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(NullFactory.class.getName()));
	}

	@Test
	public void getFactoryShouldReturnFactoryInConfigurationRatherThanDynamicBeanFactory() throws Exception {
		Configuration configuration =
		        new ConfigurationBuilder().overrideFactory(PROPERTY_NAME, new NullFactory()).build();
		Factory<?> factory =
		        factoryLookupStrategy.getFactory(beanInformationMock, PROPERTY_NAME, BasicBean.class, configuration);
		assertThat("Incorrect factory.", factory.getClass().getName(), is(NullFactory.class.getName()));
	}

	public enum Color {
		RED, BLUE, GREEN
	}

	public static class BasicBean {
	}
}