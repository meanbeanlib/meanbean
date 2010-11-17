package org.meanbean.test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.lang.Factory;

public class NotEqualsTesterTest {

	private final NotEqualsTester notEqualsTester = new NotEqualsTester();

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventNullFactory() throws Exception {
		notEqualsTester.testEquals(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventFactoryThatCreatesNullObjects() throws Exception {
		notEqualsTester.testEquals(new NullFactory());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventNullUnusedProperties() throws Exception {
		notEqualsTester.testEquals(new BeanFactory(), null);
	}

	@Test
	public void testEqualsShouldAcceptNoUnusedProperties() throws Exception {
		notEqualsTester.testEquals(new BeanFactory());
	}

	@Test
	public void testEqualsShouldAcceptEmptyUnusedProperties() throws Exception {
		notEqualsTester.testEquals(new BeanFactory(), new String[] {});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventFactoryThatCreatesNonEqualObjects() throws Exception {
		notEqualsTester.testEquals(new Factory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
			public FieldDrivenEqualsBean create() {
				// 2nd object created by factory always returns false from equals(); others always return true
				return new FieldDrivenEqualsBean(counter++ != 1);
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventFactoryThatCreatesObjectsWithDifferentPropertyValues() throws Exception {
		notEqualsTester.testEquals(new Factory<FieldDrivenEqualsBean>() {
			private int counter;

			@Override
			public FieldDrivenEqualsBean create() {
				FieldDrivenEqualsBean bean = new FieldDrivenEqualsBean(true);// equal to everything
				bean.setName("NAME" + counter++);// property has different value each time
				return bean;
			}
		});
	}

	// unused property
	// used property

	@Test(expected = BeanTestException.class)
	public void testEqualsShouldWrapExceptionsThrownWhenInvokingSetterMethodInBeanTestException() throws Exception {
		notEqualsTester.testEquals(new Factory<BeanWithBadSetterMethod>() {
			@Override
			public BeanWithBadSetterMethod create() {
				return new BeanWithBadSetterMethod();
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void testEqualsShouldWrapExceptionsThrownWhenInvokingGetterMethodInBeanTestException() throws Exception {
		notEqualsTester.testEquals(new Factory<BeanWithBadGetterMethod>() {
			@Override
			public BeanWithBadGetterMethod create() {
				return new BeanWithBadGetterMethod();
			}
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEqualsShouldPreventNullPropertyValues() throws Exception {
		notEqualsTester.testEquals(new Factory<Bean>() {
			@Override
			public Bean create() {
				return new Bean(); // null name property
			}
		});
	}

	@Test(expected = BeanTestException.class)
	public void testEqualsShouldWrapNoSuchFactoryExceptionInBeanTestException() throws Exception {
		notEqualsTester.testEquals(new Factory<CompositeBean>() {
			@Override
			public CompositeBean create() {
				CompositeBean bean = new CompositeBean();
				bean.setName("TEST_VALUE");
				bean.setNonBean(new NonBean("ANOTHER_TEST_VALUE"));
				return bean;
			}
		});
	}

	@Test(expected = AssertionError.class)
	public void testEqualsShouldThrowAssertionErrorWhenEqualityShouldNotHaveChangedButDid() throws Exception {
		notEqualsTester.testEquals(new Factory<MultiPropertyBean>() {
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
	public void testEqualsShouldThrowAssertionErrorWhenEqualityShouldHaveChangedButDidNot() throws Exception {
		notEqualsTester.testEquals(new Factory<BrokenEqualsMultiPropertyBean>() {
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
	public void testEqualsShouldNotThrowAssertionErrorWhenTestPasses() throws Exception {
		notEqualsTester.testEquals(new Factory<MultiPropertyBean>() {
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
	public void testEqualsShouldIgnoreProperties() throws Exception {
		Configuration configuration = new ConfigurationBuilder().ignoreProperty("lastName").build();
		notEqualsTester.testEquals(new Factory<MultiPropertyBean>() {
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
	public void testEqualsShouldUseOverrideFactory() throws Exception {
		final String lastName = "MY_SPECIAL_TEST_STRING";
		Configuration configuration = new ConfigurationBuilder().overrideFactory("lastName", new Factory<String>() {
			@Override
			public String create() {
				return lastName;
			}
		}).build();
		notEqualsTester.testEquals(new Factory<MultiPropertyBean>() {
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
		FactoryCollection factoryRepository = notEqualsTester.getFactoryCollection();
		assertThat("Failed to get FactoryRepository.", factoryRepository, is(not(nullValue())));
		@SuppressWarnings("unchecked")
		Factory<String> stringFactory = (Factory<String>) factoryRepository.getFactory(String.class);
		String randomString = stringFactory.create();
		assertThat("Failed to get random String from FactoryRepository.", randomString, is(not(nullValue())));
	}

	private static class CompositeBean extends Bean {

		private NonBean nonBean;

		public NonBean getNonBean() {
			return nonBean;
		}

		public void setNonBean(NonBean nonBean) {
			this.nonBean = nonBean;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("CompositeBean [nonBean=").append(getNonBean()).append("]");
			return builder.toString();
		}
	}

	private static class NonBean {

		private final String value;

		public NonBean(String value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NonBean other = (NonBean) obj;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}
	}

	private static class BeanWithBadSetterMethod extends Bean {
		@Override
		public void setName(String name) {
			throw new RuntimeException("BAD SETTER METHOD");
		}

		@Override
		public String getName() {
			return "TEST_VALUE";
		}
	}

	private static class BeanWithBadGetterMethod extends Bean {
		@Override
		public String getName() {
			throw new RuntimeException("BAD SETTER METHOD");
		}
	}

	private static class MultiPropertyBean {

		private String firstName;
		private String lastName;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
			result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiPropertyBean other = (MultiPropertyBean) obj;
			if (firstName == null) {
				if (other.firstName != null)
					return false;
			} else if (!firstName.equals(other.firstName))
				return false;
			if (lastName == null) {
				if (other.lastName != null)
					return false;
			} else if (!lastName.equals(other.lastName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MultiPropertyBean [firstName=").append(getFirstName()).append(", lastName=")
			        .append(getLastName()).append("]");
			return builder.toString();
		}
	}

	private static class BrokenEqualsMultiPropertyBean extends MultiPropertyBean {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getFirstName() == null) ? 0 : getFirstName().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MultiPropertyBean other = (MultiPropertyBean) obj;
			if (getFirstName() == null) {
				if (other.getFirstName() != null)
					return false;
			} else if (!getFirstName().equals(other.getFirstName()))
				return false;
			return true;
		}

	}
}