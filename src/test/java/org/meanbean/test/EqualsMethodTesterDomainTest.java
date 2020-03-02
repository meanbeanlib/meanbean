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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class EqualsMethodTesterDomainTest {

	private final EqualsMethodTester equalsTester = new EqualsMethodTester();

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
		    assertThat(e.getMessage(), startsWith("objects that differ due to supposedly significant property [id] were considered equal."));
            assertThat(e.getMessage(), endsWith("is property [id] actually insignificant?"));
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
