package org.meanbean.bean.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.meanbean.bean.info.JavaBeanInformationFactory;

public class DynamicBeanFactoryTest {

	private final JavaBeanInformationFactory beanInformationFactory = new JavaBeanInformationFactory();

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullBeanInformation() throws Exception {
		new DynamicBeanFactory(null);
	}

	@Test(expected = BeanCreationException.class)
	public void createShouldRethrowInstantiationExceptionAsBeanCreationException() throws Exception {
		new DynamicBeanFactory(beanInformationFactory.create(ClassThatThrowsInstantiationException.class)).create();
	}

	@Test(expected = BeanCreationException.class)
	public void createShouldRethrowIllegalAccessExceptionAsBeanCreationException() throws Exception {
		new DynamicBeanFactory(beanInformationFactory.create(ClassThatThrowsIllegalAccessException.class)).create();
	}

	@Test
	public void createShouldCreateJavaObjectsThatHaveNoArgConstructors() throws Exception {
		Object object = new DynamicBeanFactory(beanInformationFactory.create(String.class)).create();
		assertTrue("Should be a String.", object instanceof String);
		assertThat("Incorrect value.", ((String) object).length(), is(0));
	}

	@Test(expected = BeanCreationException.class)
	public void createShouldNotCreateJavaWrapperObjectsThatDoNotHaveNoArgConstructors() throws Exception {
		new DynamicBeanFactory(beanInformationFactory.create(Long.class)).create();
	}

	@Test
	public void createShouldCreateCustomDataTypesThatHaveNoArgConstructors() throws Exception {
		Object object = new DynamicBeanFactory(beanInformationFactory.create(SimpleClass.class)).create();
		assertTrue("Should be a String.", object instanceof SimpleClass);
		assertThat("Incorrect value.", ((SimpleClass) object).getName(), is(nullValue()));
	}

	@Test
	public void createShouldCreateNewInstanceEachInvocation() throws Exception {
		DynamicBeanFactory dynamicBeanFactory = new DynamicBeanFactory(beanInformationFactory.create(String.class));
		assertThat("Should be different instances.", dynamicBeanFactory.create(),
		        is(not(sameInstance(dynamicBeanFactory.create()))));
	}

	static class ClassThatThrowsInstantiationException {

		public ClassThatThrowsInstantiationException() throws InstantiationException {
			throw new InstantiationException();
		}

	}

	static class ClassThatThrowsIllegalAccessException {

		public ClassThatThrowsIllegalAccessException() throws IllegalAccessException {
			throw new IllegalAccessException();
		}

	}

	static class SimpleClass {

		private String name;

		public SimpleClass() {
		}

		public SimpleClass(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}