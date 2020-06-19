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

import org.junit.Test;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.ArrayPropertyBean;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.BeanWithBadGetterMethod;
import org.meanbean.test.beans.BeanWithBadSetterMethod;
import org.meanbean.test.beans.BeanWithNonBeanProperty;
import org.meanbean.test.beans.BrokenEqualsMultiPropertyBean;
import org.meanbean.test.beans.CounterDrivenEqualsBeanFactory;
import org.meanbean.test.beans.DifferentTypeAcceptingBean;
import org.meanbean.test.beans.DifferentTypeAcceptingBeanFactory;
import org.meanbean.test.beans.FieldDrivenEqualsBean;
import org.meanbean.test.beans.InvocationCountingFactoryWrapper;
import org.meanbean.test.beans.MultiPropertyBean;
import org.meanbean.test.beans.MultiPropertyBeanFactory;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NonReflexiveBean;
import org.meanbean.test.beans.NonReflexiveBeanFactory;
import org.meanbean.test.beans.NullAcceptingBean;
import org.meanbean.test.beans.NullAcceptingBeanFactory;
import org.meanbean.test.beans.NullEquivalentFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EqualsMethodTesterTest {

	private FactoryCollection factoryCollection = FactoryCollection.getInstance();
	private final EqualsMethodTester equalsTester = new EqualsMethodTester();

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullFactory() throws Exception {
		equalsTester.testEqualsMethod((EquivalentFactory<?>) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		equalsTester.testEqualsMethod(new NullEquivalentFactory());
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
		equalsTester.testEqualsMethod(new EquivalentFactory<FieldDrivenEqualsBean>() {
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
		equalsTester.testEqualsMethod(new EquivalentFactory<FieldDrivenEqualsBean>() {
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
		equalsTester.testEqualsMethod(new EquivalentFactory<BeanWithBadSetterMethod>() {
			@Override
            public BeanWithBadSetterMethod create() {
				return new BeanWithBadSetterMethod();
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void testEqualsMethodShouldWrapExceptionsThrownWhenInvokingGetterMethodInBeanTestException()
	        throws Exception {
		equalsTester.testEqualsMethod(new EquivalentFactory<BeanWithBadGetterMethod>() {
			@Override
            public BeanWithBadGetterMethod create() {
				return new BeanWithBadGetterMethod();
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullPropertyValues() throws Exception {
		equalsTester.testEqualsMethod(new EquivalentFactory<Bean>() {
			@Override
            public Bean create() {
				return new Bean(); // null name property
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void testEqualsMethodShouldWrapNoSuchFactoryExceptionInBeanTestException() throws Exception {
		equalsTester.testEqualsMethod(new EquivalentFactory<BeanWithNonBeanProperty>() {
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
		equalsTester.testEqualsMethod(new MultiPropertyBeanFactory(), "lastName");
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() throws Exception {
		equalsTester.testEqualsMethod(new EquivalentFactory<BrokenEqualsMultiPropertyBean>() {
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
		equalsTester.testEqualsMethod(new MultiPropertyBeanFactory());
	}

	@Test
	public void testEqualsMethodShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		equalsTester.testEqualsMethod(new MultiPropertyBeanFactory(), configuration, "lastName");
	}

	@Test
	public void testEqualsMethodShouldUseOverrideFactory() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		equalsTester.testEqualsMethod(new BeanFactory(), configuration);
		assertThat("custom factory was not used", factory.getInvocationCount(),
		        is(BeanTester.TEST_ITERATIONS_PER_BEAN));
	}

	@Test
	public void testEqualsShouldTestPropertySignificanceConfigurationIterationsTimes() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).iterations(5).build();
		equalsTester.testEqualsMethod(new BeanFactory(), configuration);
		assertThat("global iterations was not used", factory.getInvocationCount(), is(configuration.getIterations()));
	}

	@Test
	public void testEqualsShouldTestPropertySignificanceGlobalIterationsTimes() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		equalsTester.testEqualsMethod(new BeanFactory(), configuration);
		assertThat("global iterations was not used", factory.getInvocationCount(),
		        is(BeanTester.TEST_ITERATIONS_PER_BEAN));
	}

	@Test
	public void testEqualsShouldPreventUnrecognisedPropertyAndPermitRecognisedProperty() throws Exception {
		String unrecognisedPropertyName = "UNRECOGNISED_PROPERTY";
		BeanFactory beanFactory = new BeanFactory();
		Bean bean = beanFactory.create();
		try {
			equalsTester.testEqualsMethod(beanFactory, "name", unrecognisedPropertyName);
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName + "] do not exist on "
			                + bean.getClass().getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}

	@Test
	public void testEqualsShouldPreventUnrecognisedPropertiesAndPermitRecognisedProperties() throws Exception {
		String unrecognisedPropertyName1 = "UNRECOGNISED_PROPERTY_1";
		String unrecognisedPropertyName2 = "UNRECOGNISED_PROPERTY_2";
		MultiPropertyBeanFactory beanFactory = new MultiPropertyBeanFactory();
		MultiPropertyBean bean = beanFactory.create();
		try {
			equalsTester.testEqualsMethod(beanFactory, "firstName", unrecognisedPropertyName1, "lastName",
			        unrecognisedPropertyName2);
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName1 + "," + unrecognisedPropertyName2
			                + "] do not exist on " + bean.getClass().getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodShouldPreventNullClass() throws Exception {
		equalsTester.testEqualsMethod((Class<?>) null);
	}

	@Test(expected = ObjectCreationException.class)
	public void testEqualsMethodWithNonBeanClassWillThrowObjectCreationException() throws Exception {
		equalsTester.testEqualsMethod(NonBean.class);
	}

	@Test
	public void testEqualsMethodByClassShouldNotThrowAssertionErrorWhenEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Bean.class);
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodByClassShouldThrowAssertionErrorWhenEqualsIsNotReflexive() throws Exception {
		equalsTester.testEqualsMethod(NonReflexiveBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodByClassShouldThrowAssertionErrorWhenEqualsIsTrueForNull() throws Exception {
		equalsTester.testEqualsMethod(NullAcceptingBean.class);
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodByClassShouldThrowAssertionErrorWhenEqualsIsTrueForDifferentType() throws Exception {
		equalsTester.testEqualsMethod(DifferentTypeAcceptingBean.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsMethodByClassShouldPreventNullInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(Bean.class, (String[]) null);
	}

	@Test
	public void testEqualsMethodByClassShouldAcceptNoInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(Bean.class);
	}

	@Test
	public void testEqualsMethodByClassShouldAcceptEmptyInsignificantProperties() throws Exception {
		equalsTester.testEqualsMethod(Bean.class, new String[] {});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodByClassShouldThrowAssertionErrorWhenEqualityShouldNotHaveChangedButDid() {
		equalsTester.testEqualsMethod(MultiPropertyBean.class, "lastName");
	}

	@Test(expected = AssertionError.class)
	public void testEqualsMethodByClassShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() {
		equalsTester.testEqualsMethod(BrokenEqualsMultiPropertyBean.class);
	}

	@Test
	public void testEqualsMethodByClassShouldNotThrowAssertionErrorWhenTestPasses() throws Exception {
		equalsTester.testEqualsMethod(MultiPropertyBean.class);
	}

	@Test
	public void testEqualsMethodByClassShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		equalsTester.testEqualsMethod(MultiPropertyBean.class, configuration, "lastName");
	}

	@Test
	public void testEqualsMethodByClassShouldUseOverrideFactory() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		equalsTester.testEqualsMethod(Bean.class, configuration);
		assertThat("custom factory was not used", factory.getInvocationCount(),
		        is(BeanTester.TEST_ITERATIONS_PER_BEAN));
	}

	@Test
	public void testEqualsMethodByClassShouldTestPropertySignificanceConfigurationIterationsTimes() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).iterations(5).build();
		equalsTester.testEqualsMethod(Bean.class, configuration);
		assertThat("global iterations was not used", factory.getInvocationCount(), is(configuration.getIterations()));
	}

	@Test
	public void testEqualsMethodByClassShouldTestPropertySignificanceGlobalIterationsTimes() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		equalsTester.testEqualsMethod(Bean.class, configuration);
		assertThat("global iterations was not used", factory.getInvocationCount(),
		        is(BeanTester.TEST_ITERATIONS_PER_BEAN));
	}

	@Test
	public void testEqualsMethodByClassShouldPreventUnrecognisedPropertyAndPermitRecognisedProperty() throws Exception {
		String unrecognisedPropertyName = "UNRECOGNISED_PROPERTY";
		try {
			equalsTester.testEqualsMethod(Bean.class, "name", unrecognisedPropertyName);
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName + "] do not exist on "
			                + Bean.class.getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}

	@Test
	public void testEqualsMethodByClassShouldPreventUnrecognisedPropertiesAndPermitRecognisedProperties()
	        throws Exception {
		String unrecognisedPropertyName1 = "UNRECOGNISED_PROPERTY_1";
		String unrecognisedPropertyName2 = "UNRECOGNISED_PROPERTY_2";
		try {
			equalsTester.testEqualsMethod(MultiPropertyBean.class, "firstName", unrecognisedPropertyName1, "lastName",
			        unrecognisedPropertyName2);
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName1 + "," + unrecognisedPropertyName2
			                + "] do not exist on " + MultiPropertyBean.class.getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}

	@Test
	public void testArrayPropertyBean() {
		for (int i = 0; i < 10; i++) {
			BeanVerifier.forClass(ArrayPropertyBean.class)
					.verifyEqualsAndHashCode();
		}
	}
}
