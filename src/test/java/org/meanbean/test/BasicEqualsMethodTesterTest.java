package org.meanbean.test;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.BeanWithBadGetterMethod;
import org.meanbean.test.beans.BeanWithBadSetterMethod;
import org.meanbean.test.beans.BeanWithNonBeanProperty;
import org.meanbean.test.beans.BrokenEqualsMultiPropertyBean;
import org.meanbean.test.beans.CounterDrivenEqualsBeanFactory;
import org.meanbean.test.beans.DifferentTypeAcceptingBeanFactory;
import org.meanbean.test.beans.FieldDrivenEqualsBean;
import org.meanbean.test.beans.MultiPropertyBean;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NonReflexiveBeanFactory;
import org.meanbean.test.beans.NullAcceptingBeanFactory;
import org.meanbean.test.beans.NullFactory;

public class BasicEqualsMethodTesterTest {

	private final EqualsMethodTester equalsTester = new BasicEqualsMethodTester();

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsMethod(new NullFactory());
	}

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(new BeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		equalsTester.testEqualsMethod(new NonReflexiveBeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsInconsistent() throws Exception {
		equalsTester.testEqualsMethod(new CounterDrivenEqualsBeanFactory(97));
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		equalsTester.testEqualsMethod(new NullAcceptingBeanFactory());
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		equalsTester.testEqualsMethod(new DifferentTypeAcceptingBeanFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(new BeanFactory(), (String[]) null);
	}

	@Test
	public void testEqualsMethodShouldAcceptNoInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(new BeanFactory());
	}

	@Test
	public void testEqualsMethodShouldAcceptEmptyInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(new BeanFactory(), new String[] {});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventFactoryThatCreatesNonEqualObjects() throws Exception {
		equalsTester.testEqualsMethod(new Factory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
			public FieldDrivenEqualsBean create() {
				// 2nd object created by factory always returns false from equals(); others always return true
				return new FieldDrivenEqualsBean(counter++ != 1);
			}
		});
	}

	@Test(expected = Throwable.class)
	public void testEqualsMethodShouldPreventFactoryThatCreatesObjectsWithDifferentPropertyValues() throws Exception {
		equalsTester.testEqualsMethod(new Factory<FieldDrivenEqualsBean>() {
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
	public void testEqualsMethodShouldWrapExceptionsThrownWhenInvokingSetterMethodInBeanTestException()
	        throws Exception {
		equalsTester.testEqualsMethod(new Factory<BeanWithBadSetterMethod>() {
			@Override
			public BeanWithBadSetterMethod create() {
				return new BeanWithBadSetterMethod();
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void testEqualsMethodShouldWrapExceptionsThrownWhenInvokingGetterMethodInBeanTestException()
	        throws Exception {
		equalsTester.testEqualsMethod(new Factory<BeanWithBadGetterMethod>() {
			@Override
			public BeanWithBadGetterMethod create() {
				return new BeanWithBadGetterMethod();
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullPropertyValues() throws Exception {
		equalsTester.testEqualsMethod(new Factory<Bean>() {
			@Override
			public Bean create() {
				return new Bean(); // null name property
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void testEqualsMethodShouldWrapNoSuchFactoryExceptionInBeanTestException() throws Exception {
		equalsTester.testEqualsMethod(new Factory<BeanWithNonBeanProperty>() {
			@Override
			public BeanWithNonBeanProperty create() {
				BeanWithNonBeanProperty bean = new BeanWithNonBeanProperty();
				bean.setName("TEST_VALUE");
				bean.setNonBean(new NonBean("ANOTHER_TEST_VALUE"));
				return bean;
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldNotHaveChangedButDid() throws Exception {
		equalsTester.testEqualsMethod(new Factory<MultiPropertyBean>() {
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
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() throws Exception {
		equalsTester.testEqualsMethod(new Factory<BrokenEqualsMultiPropertyBean>() {
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
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenTestPasses() throws Exception {
		equalsTester.testEqualsMethod(new Factory<MultiPropertyBean>() {
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
	public void testEqualsMethodShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		equalsTester.testEqualsMethod(new Factory<MultiPropertyBean>() {
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
	public void testEqualsMethodShouldUseOverrideFactory() throws Exception {
		final String lastName = "MY_SPECIAL_TEST_STRING";
		Configuration configuration = new ConfigurationBuilder().overrideFactory("lastName", new Factory<String>() {
			@Override
			public String create() {
				return lastName;
			}
		}).build();
		equalsTester.testEqualsMethod(new Factory<MultiPropertyBean>() {
			@Override
			public MultiPropertyBean create() {
				MultiPropertyBean bean = new MultiPropertyBean();
				bean.setFirstName("FIRST_NAME");
				bean.setLastName(lastName);
				return bean;
			}
		}, configuration);
	}
}