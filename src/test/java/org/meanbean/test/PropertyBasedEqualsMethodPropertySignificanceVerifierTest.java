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
import org.meanbean.bean.info.BeanInformation;
import org.meanbean.bean.info.JavaBeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.lang.Factory;
import org.meanbean.test.beans.Bean;
import org.meanbean.test.beans.BeanFactory;
import org.meanbean.test.beans.BeanWithBadGetterMethod;
import org.meanbean.test.beans.BeanWithBadSetterMethod;
import org.meanbean.test.beans.BeanWithNonBeanProperty;
import org.meanbean.test.beans.BrokenEqualsMultiPropertyBean;
import org.meanbean.test.beans.ComplexBeanFactory;
import org.meanbean.test.beans.CounterDrivenEqualsBeanFactory;
import org.meanbean.test.beans.FieldDrivenEqualsBean;
import org.meanbean.test.beans.FieldDrivenEqualsBeanFactory;
import org.meanbean.test.beans.IncrementalStringFactory;
import org.meanbean.test.beans.InvocationCountingFactoryWrapper;
import org.meanbean.test.beans.MultiPropertyBean;
import org.meanbean.test.beans.MultiPropertyBeanFactory;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.NullEquivalentFactory;
import org.meanbean.test.beans.SelfReferencingBeanFactory;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class PropertyBasedEqualsMethodPropertySignificanceVerifierTest {

	private final FactoryCollection factoryCollection = FactoryCollection.getInstance();
	private final PropertyBasedEqualsMethodPropertySignificanceVerifier verifier =
	        new PropertyBasedEqualsMethodPropertySignificanceVerifier();

	@Test
	public void shouldGetFactoryRepository() throws Exception {
		assertThat("Failed to get FactoryRepository.", factoryCollection, is(not(nullValue())));
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		String randomString = stringFactory.create();
		assertThat("Failed to get random String from FactoryRepository.", randomString, is(not(nullValue())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventNullFactory() throws Exception {
		verifier.verifyEqualsMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		verifier.verifyEqualsMethod(new NullEquivalentFactory());
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
		verifier.verifyEqualsMethod(new EquivalentFactory<FieldDrivenEqualsBean>() {
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
		verifier.verifyEqualsMethod(new EquivalentFactory<FieldDrivenEqualsBean>() {
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
		verifier.verifyEqualsMethod(new EquivalentFactory<BeanWithBadSetterMethod>() {
			@Override
            public BeanWithBadSetterMethod create() {
				return new BeanWithBadSetterMethod();
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void verifyEqualsMethodShouldWrapExceptionsThrownWhenInvokingGetterMethodInBeanTestException()
	        throws Exception {
		verifier.verifyEqualsMethod(new EquivalentFactory<BeanWithBadGetterMethod>() {
			@Override
            public BeanWithBadGetterMethod create() {
				return new BeanWithBadGetterMethod();
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void verifyEqualsMethodShouldPreventNullPropertyValues() throws Exception {
		verifier.verifyEqualsMethod(new EquivalentFactory<Bean>() {
			@Override
            public Bean create() {
				return new Bean(); // null name property
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void verifyEqualsMethodShouldWrapNoSuchFactoryExceptionInBeanTestException() throws Exception {
		verifier.verifyEqualsMethod(new EquivalentFactory<Object>() {
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
		verifier.verifyEqualsMethod(new MultiPropertyBeanFactory(), "lastName");
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsMethodShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() throws Exception {
		verifier.verifyEqualsMethod(new EquivalentFactory<BrokenEqualsMultiPropertyBean>() {

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
	public void verifyEqualsMethodShouldNotThrowAssertionErrorWhenTestPassesWithMultiPropertyBean() throws Exception {
		verifier.verifyEqualsMethod(new MultiPropertyBeanFactory());
	}

	@Test
	public void verifyEqualsMethodShouldNotThrowAssertionErrorWhenTestPassesWithSelfReferencingBean() throws Exception {
		verifier.verifyEqualsMethod(new SelfReferencingBeanFactory());
	}

	@Test
	public void verifyEqualsMethodShouldNotThrowAssertionErrorWhenTestPassesWithComplexBean() throws Exception {
		// This tests whether the verifier can cope with non-readable/non-writable properties, etc.
		verifier.verifyEqualsMethod(new ComplexBeanFactory(), "id");
	}

	@Test
	public void verifyEqualsMethodShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		verifier.verifyEqualsMethod(new MultiPropertyBeanFactory(), configuration, "lastName");
	}

	@Test
	public void verifyEqualsMethodShouldUseOverrideFactory() throws Exception {
		Factory<String> stringFactory = factoryCollection.getFactory(String.class);
		InvocationCountingFactoryWrapper<String> factory = new InvocationCountingFactoryWrapper<String>(stringFactory);
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", factory).build();
		verifier.verifyEqualsMethod(new BeanFactory(), configuration);
		assertThat("custom factory was not used", factory.getInvocationCount(), is(1));
	}

	@Test
	public void verifyEqualsMethodShouldPreventUnrecognisedPropertyAndPermitRecognisedProperty() throws Exception {
		String unrecognisedPropertyName = "UNRECOGNISED_PROPERTY";
		BeanFactory beanFactory = new BeanFactory();
		Bean bean = beanFactory.create();
		try {
			verifier.verifyEqualsMethod(beanFactory, "name", unrecognisedPropertyName);
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
	public void verifyEqualsMethodShouldPreventUnrecognisedPropertiesAndPermitRecognisedProperties() throws Exception {
		String unrecognisedPropertyName1 = "UNRECOGNISED_PROPERTY_1";
		String unrecognisedPropertyName2 = "UNRECOGNISED_PROPERTY_2";
		MultiPropertyBeanFactory beanFactory = new MultiPropertyBeanFactory();
		MultiPropertyBean bean = beanFactory.create();
		try {
			verifier.verifyEqualsMethod(beanFactory, "firstName", unrecognisedPropertyName1, "lastName",
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

	@Test
	public void ensureInsignificantPropertiesExistShouldThrowExceptionForSingleUnrecognisedProperty() throws Exception {
		String unrecognisedPropertyName = "UNRECOGNISED_PROPERTY";
		BeanInformation beanInformation = new JavaBeanInformationFactory().create(Bean.class);
		try {
			verifier.ensureInsignificantPropertiesExist(beanInformation,
			        Arrays.asList("name", unrecognisedPropertyName));
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
	public void ensureInsignificantPropertiesExistShouldThrowExceptionForMultipleUnrecognisedProperties()
	        throws Exception {
		String unrecognisedPropertyName1 = "UNRECOGNISED_PROPERTY_1";
		String unrecognisedPropertyName2 = "UNRECOGNISED_PROPERTY_2";
		try {
			verifier.ensureInsignificantPropertiesExist(
			        new JavaBeanInformationFactory().create(MultiPropertyBean.class),
			        Arrays.asList("firstName", unrecognisedPropertyName1, "lastName", unrecognisedPropertyName2));
		} catch (IllegalArgumentException e) {
			String expectedExceptionMessage =
			        "Insignificant properties [" + unrecognisedPropertyName1 + "," + unrecognisedPropertyName2
			                + "] do not exist on " + MultiPropertyBean.class.getName() + ".";
			assertEquals("incorrect exception message", expectedExceptionMessage, e.getMessage());
			return;
		}
		fail("exception was not thrown");
	}

	// TODO
	// @Test(expected = AssertionError.class)
	// public void verifyEqualsMethodShouldUseOverrideFactory() throws Exception {
	// final String lastName = "MY_SPECIAL_TEST_STRING";
	// Configuration configuration = new ConfigurationBuilder().overrideFactory("lastName", new Factory<String>() {
	// @Override
	// public String create() {
	// return lastName;
	// }
	// }).build();
	// verifier.verifyEqualsMethod(new Factory<MultiPropertyBean>() {
	// @Override
	// public MultiPropertyBean create() {
	// MultiPropertyBean bean = new MultiPropertyBean();
	// bean.setFirstName("FIRST_NAME");
	// bean.setLastName(lastName);
	// return bean;
	// }
	// }, configuration);
	// }

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenValuesDifferButObjectsStillEqualForSignificantProperty()
	        throws Exception {
		verifier.verifyEqualsMethod(new FieldDrivenEqualsBeanFactory(true),
		        new ConfigurationBuilder().overrideFactory("name", new IncrementalStringFactory()).build());
	}

	@Test
	public void verifyEqualsShouldNotThrowAssertionErrorWhenValuesDifferAndObjectsNotEqualForSignificantProperty()
	        throws Exception {
		verifier.verifyEqualsMethod(new BeanFactory(),
		        new ConfigurationBuilder().overrideFactory("name", new IncrementalStringFactory()).build());
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenValuesSameButObjectsNotEqualForSignificantProperty()
	        throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", new Factory<String>() {
			@Override
            public String create() {
				return CounterDrivenEqualsBeanFactory.NAME;
			}
		}).build();
		verifier.verifyEqualsMethod(new CounterDrivenEqualsBeanFactory(1), configuration);
	}

	@Test
	public void verifyEqualsShouldNotThrowAssertionErrorWhenValuesSameAndObjectsEqualForSignificantProperty()
	        throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", new Factory<String>() {
			@Override
            public String create() {
				return BeanFactory.NAME;
			}
		}).build();
		verifier.verifyEqualsMethod(new BeanFactory(), configuration);
	}

	@Test
	public void verifyEqualsShouldNotThrowAssertionErrorWhenValuesDifferAndObjectsEqualForInsignificantProperty()
	        throws Exception {
		verifier.verifyEqualsMethod(new FieldDrivenEqualsBeanFactory(true),
		        new ConfigurationBuilder().overrideFactory("name", new IncrementalStringFactory()).build(), "name");
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenValuesDifferAndObjectsNotEqualForInsignificantProperty()
	        throws Exception {
		verifier.verifyEqualsMethod(new BeanFactory(),
		        new ConfigurationBuilder().overrideFactory("name", new IncrementalStringFactory()).build(), "name");
	}

	@Test(expected = AssertionError.class)
	public void verifyEqualsShouldThrowAssertionErrorWhenValuesSameButObjectsNotEqualForInsignificantProperty()
	        throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", new Factory<String>() {
			@Override
            public String create() {
				return CounterDrivenEqualsBeanFactory.NAME;
			}
		}).build();
		verifier.verifyEqualsMethod(new CounterDrivenEqualsBeanFactory(1), configuration, "name");
	}

	@Test
	public void verifyEqualsShouldNotThrowAssertionErrorWhenValuesSameAndObjectsEqualForInsignificantProperty()
	        throws Exception {
		Configuration configuration = new ConfigurationBuilder().overrideFactory("name", new Factory<String>() {
			@Override
            public String create() {
				return BeanFactory.NAME;
			}
		}).build();
		verifier.verifyEqualsMethod(new BeanFactory(), configuration);
	}
}
