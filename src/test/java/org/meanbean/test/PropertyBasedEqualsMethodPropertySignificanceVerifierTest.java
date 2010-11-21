package org.meanbean.test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.BeanWithBadGetterMethod;
import org.meanbean.test.beans.BeanWithBadSetterMethod;
import org.meanbean.test.beans.BeanWithNonBeanProperty;
import org.meanbean.test.beans.BrokenEqualsMultiPropertyBean;
import org.meanbean.test.beans.FieldDrivenEqualsBean;
import org.meanbean.test.beans.MultiPropertyBean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NullFactory;

public class PropertyBasedEqualsMethodPropertySignificanceVerifierTest {

	private final PropertyBasedEqualsMethodPropertySignificanceVerifier verifier = new PropertyBasedEqualsMethodPropertySignificanceVerifier();

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsMethod(new NullFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventNullInsignificantProperties() throws Exception {
		verifier.verifyEqualsMethod(new BeanFactory(), (String[]) null);
	}

	@Test
	public void verifyEqualsMethodShouldAcceptNoInsignificantProperties() throws Exception {
		verifier.verifyEqualsMethod(new BeanFactory());
	}

	@Test
	public void verifyEqualsMethodShouldAcceptEmptyInsignificantProperties() throws Exception {
		verifier.verifyEqualsMethod(new BeanFactory(), new String[] {});
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventFactoryThatCreatesNonEqualObjects() throws Exception {
		verifier.verifyEqualsMethod(new Factory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
			public FieldDrivenEqualsBean create() {
				// 2nd object created by factory always returns false from equals(); others always return true
				return new FieldDrivenEqualsBean(counter++ != 1);
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventFactoryThatCreatesObjectsWithDifferentPropertyValues() throws Exception {
		verifier.verifyEqualsMethod(new Factory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
			public FieldDrivenEqualsBean create() {
				FieldDrivenEqualsBean bean = new FieldDrivenEqualsBean(true);// equal to everything
				bean.setName("NAME" + counter++);// property has different value each time
				return bean;
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void verifyEqualsMethodShouldWrapExceptionsThrownWhenInvokingSetterMethodInBeanTestException()
	        throws Exception {
		verifier.verifyEqualsMethod(new Factory<BeanWithBadSetterMethod>() {
			@Override
			public BeanWithBadSetterMethod create() {
				return new BeanWithBadSetterMethod();
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void verifyEqualsMethodShouldWrapExceptionsThrownWhenInvokingGetterMethodInBeanTestException()
	        throws Exception {
		verifier.verifyEqualsMethod(new Factory<BeanWithBadGetterMethod>() {
			@Override
			public BeanWithBadGetterMethod create() {
				return new BeanWithBadGetterMethod();
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventNullPropertyValues() throws Exception {
		verifier.verifyEqualsMethod(new Factory<Bean>() {
			@Override
			public Bean create() {
				return new Bean(); // null name property
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void verifyEqualsMethodShouldWrapNoSuchFactoryExceptionInBeanTestException() throws Exception {
		verifier.verifyEqualsMethod(new Factory<Object>() {
			@Override
			public Object create() {
				BeanWithNonBeanProperty bean = new BeanWithNonBeanProperty();
				bean.setName("TEST_VALUE");
				bean.setNonBean(new NonBean("ANOTHER_TEST_VALUE"));
				return bean;
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldNotHaveChangedButDid() throws Exception {
		verifier.verifyEqualsMethod(new Factory<MultiPropertyBean>() {
			@Override
			public MultiPropertyBean create() {
				MultiPropertyBean bean = new MultiPropertyBean();
				bean.setFirstName("FIRST_NAME");
				bean.setLastName("LAST_NAME");
				return bean;
			}
		}, "lastName");
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() throws Exception {
		verifier.verifyEqualsMethod(new Factory<BrokenEqualsMultiPropertyBean>() {
			@Override
			public BrokenEqualsMultiPropertyBean create() {
				BrokenEqualsMultiPropertyBean bean = new BrokenEqualsMultiPropertyBean();
				bean.setFirstName("FIRST_NAME");
				bean.setLastName("LAST_NAME");
				return bean;
			}
		});
	}

	@Test
	public void verifyEqualsMethodShouldNotThrowAssertionErrorWhenTestPasses() throws Exception {
		verifier.verifyEqualsMethod(new Factory<MultiPropertyBean>() {
			@Override
			public MultiPropertyBean create() {
				MultiPropertyBean bean = new MultiPropertyBean();
				bean.setFirstName("FIRST_NAME");
				bean.setLastName("LAST_NAME");
				return bean;
			}
		});
	}

	@Test
	public void verifyEqualsMethodShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		verifier.verifyEqualsMethod(new Factory<MultiPropertyBean>() {
			@Override
			public MultiPropertyBean create() {
				MultiPropertyBean bean = new MultiPropertyBean();
				bean.setFirstName("FIRST_NAME");
				bean.setLastName("LAST_NAME");
				return bean;
			}
		}, configuration, "lastName");
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsMethodShouldUseOverrideFactory() throws Exception {
		final String lastName = "MY_SPECIAL_TEST_STRING";
		Configuration configuration = new ConfigurationBuilder().overrideFactory("lastName", new Factory<String>() {
			@Override
			public String create() {
				return lastName;
			}
		}).build();
		verifier.verifyEqualsMethod(new Factory<MultiPropertyBean>() {
			@Override
			public MultiPropertyBean create() {
				MultiPropertyBean bean = new MultiPropertyBean();
				bean.setFirstName("FIRST_NAME");
				bean.setLastName(lastName);
				return bean;
			}
		}, configuration);
	}

	@Test
	public void shouldGetFactoryRepository() throws Exception {
		FactoryCollection factoryRepository = verifier.getFactoryCollection();
		assertThat("Failed to get FactoryRepository.", factoryRepository, is(not(nullValue())));
		@SuppressWarnings("unchecked")
		Factory<String> stringFactory = (Factory<String>) factoryRepository.getFactory(String.class);
		String randomString = stringFactory.create();
		assertThat("Failed to get random String from FactoryRepository.", randomString, is(not(nullValue())));
	}
}