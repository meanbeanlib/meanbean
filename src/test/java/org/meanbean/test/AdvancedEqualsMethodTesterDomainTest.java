package org.meanbean.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.ObjectCreationException;
import org.meanbean.test.beans.domain.Address;
import org.meanbean.test.beans.domain.Company;
import org.meanbean.test.beans.domain.Customer;
import org.meanbean.test.beans.domain.Employee;
import org.meanbean.test.beans.domain.EmployeeId;
import org.meanbean.test.beans.domain.Item;
import org.meanbean.test.beans.domain.NonReflexiveEqualsAddress;
import org.meanbean.test.beans.domain.Order;
import org.meanbean.test.beans.domain.Person;
import org.meanbean.test.beans.domain.Product;
import org.meanbean.test.beans.domain.Rating;
import org.meanbean.test.beans.domain.Review;
import org.meanbean.test.beans.domain.ShippingCompany;
import org.meanbean.test.beans.domain.Status;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdvancedEqualsMethodTesterDomainTest {

	private final AdvancedEqualsMethodTester equalsTester = new AdvancedEqualsMethodTester();

	// Company

	@Test
	public void testEqualsMethodShouldThrowAssertionErrorWhenCompanyEqualsIsIncorrectDueToId() throws Exception {
		testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsIncorrectDueToId(Company.class);
	}

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenCompanyEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Company.class, "id");
	}

	// Address

	@Test
	public void testEqualsMethodShouldThrowAssertionErrorWhenAddressEqualsIsIncorrectDueToId() throws Exception {
		testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsIncorrectDueToId(Address.class);
	}

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenAddressEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Address.class, "id");
	}

	@Test
	public void testEqualsMethodShouldThrowAssertionErrorWhenAddressEqualsIsNotReflexive() throws Exception {
		try {
			equalsTester.testEqualsMethod(NonReflexiveEqualsAddress.class);
		} catch (AssertionError e) {
			assertThat(e.getMessage(), is("equals is not reflexive."));
			return;
		}
		fail("AssertionError should have been thrown for non-reflexive Address.");
	}

	// Customer

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenCustomerEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Customer.class, "id");
	}

	// Employee

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenEmployeeEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Employee.class, "id");
	}

	// EmployeeId

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenEmployeeIdEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(EmployeeId.class);
	}

	// Item

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenItemEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Item.class, "id");
	}

	// Order

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenOrderEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Order.class, "id");
	}

	// Person

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenPersonEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Person.class, "id");
	}

	// Product

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenProductEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Product.class, "id");
	}

	// Rating

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenRatingEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Rating.class);
	}

	// Review

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenReviewEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Review.class, "id");
	}

	// ShippingCompany

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenShippingCompanyEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(ShippingCompany.class, "id");
	}

	// Status

	@Test
	public void testEqualsMethodShouldNotThrowAssertionErrorWhenStatusEqualsIsCorrect() throws Exception {
		equalsTester.testEqualsMethod(Status.class);
	}

	// Utils

	private void testEqualsMethodShouldThrowAssertionErrorWhenEqualsIsIncorrectDueToId(Class<?> clazz) throws Exception {
		try {
			equalsTester.testEqualsMethod(clazz);
		} catch (AssertionError e) {
			assertTrue(e.getMessage().startsWith(
			        "objects that differ due to supposedly significant property [id] where considered equal."));
			assertTrue(e.getMessage().endsWith("is property [id] actually insignificant?"));
			return;
		}
		fail("AssertionError should have been thrown for [" + clazz.getSimpleName()
		        + "] equals that does not consider id significant.");
	}

	public void testEqualsMethodWillThrowObjectCreationExceptionWhenTryingToTestEnum(Class<?> clazz) throws Exception {
		try {
			equalsTester.testEqualsMethod(clazz);
		} catch (ObjectCreationException e) {
			assertThat(e.getMessage(), is("Failed to instantiate object of type [" + clazz.getName()
			        + "] due to InstantiationException."));
			return;
		}
		fail("ObjectCreationException should have been thrown when trying to instantiate enum.");
	}
}