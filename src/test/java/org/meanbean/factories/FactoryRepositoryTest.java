package org.meanbean.factories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class FactoryRepositoryTest {

	@Mock
	private Factory<?> mockFactory;

	@Mock
	private RandomValueGenerator randomValueGenerator;

	private FactoryRepository factoryRepository;

	@Before
	public void before() {
		factoryRepository = new FactoryRepository();
	}

	@Test
	public void constructorShouldRegisterPrimitiveFactoriesWithFactoryRepository() throws Exception {
		for (Class<?> clazz : PrimitiveFactoryPluginTest.FACTORY_CLASSES) {
			assertThat("FactoryRepository did not register primitive Factory for class [" + clazz + "].",
			        factoryRepository.hasFactory(clazz), is(true));
		}
	}

	@Test
	public void constructorShouldRegisterObjectFactoriesWithFactoryRepository() throws Exception {
		for (Class<?> clazz : ObjectFactoryPluginTest.FACTORY_CLASSES) {
			assertThat("FactoryRepository did not register object Factory for class [" + clazz + "].",
			        factoryRepository.hasFactory(clazz), is(true));
		}
	}

	@Test
	public void constructorShouldRegisterCollectionFactoriesWithFactoryRepository() throws Exception {
		for (Class<?> clazz : CollectionFactoryPluginTest.FACTORY_CLASSES) {
			assertThat("FactoryRepository did not register collection Factory for class [" + clazz + "].",
			        factoryRepository.hasFactory(clazz), is(true));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFactoryShouldPreventNullClass() throws Exception {
		factoryRepository.addFactory(null, mockFactory);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFactoryShouldPreventNullFactory() throws Exception {
		factoryRepository.addFactory(String.class, null);
	}

	@Test
	public void addFactoryShouldRegisterFactoryInFactoryRepository() throws Exception {
		Factory<?> factory = new RegisteredFactory();
		assertThat("Factory should not be registered yet.", factoryRepository.hasFactory(RegisteredTestClass.class),
		        is(false));
		factoryRepository.addFactory(RegisteredTestClass.class, factory);
		assertThat("Incorrect factory found.", factoryRepository.getFactory(RegisteredTestClass.class),
		        is(instanceOf(RegisteredFactory.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFactoryShouldPreventNullClass() throws Exception {
		factoryRepository.getFactory((Class<?>) null);
	}

	@Test(expected = NoSuchFactoryException.class)
	public void getFactoryShouldThrowExceptionForUnregisteredFactory() throws Exception {
		factoryRepository.getFactory(UnregisteredTestClass.class);
	}

	@Test
	public void getFactoryShouldReturnRegisteredFactory() throws Exception {
		assertThat("Should find factory.", factoryRepository.getFactory(String.class),
		        is(instanceOf(StringFactory.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void hasFactoryShouldPreventNullClass() throws Exception {
		factoryRepository.hasFactory((Class<?>) null);
	}

	@Test
	public void hasFactoryShouldReturnFalseForUnregisteredFactory() throws Exception {
		assertThat("Should not find any factory.", factoryRepository.hasFactory(UnregisteredTestClass.class), is(false));
	}

	@Test
	public void hasFactoryShouldReturnTrueForRegisteredFactory() throws Exception {
		assertThat("Should find factory.", factoryRepository.hasFactory(String.class), is(true));
	}

	static class RegisteredTestClass {
		// A class that will be registered in the FactoryRepository
	}

	static class UnregisteredTestClass {
		// A class that has no Factory registered against it in the FactoryRepository
	}

	static class RegisteredFactory implements Factory<RegisteredTestClass> {
		@Override
        public RegisteredTestClass create() {
			return null; // Not tested here - do nothing
		}
	}
}