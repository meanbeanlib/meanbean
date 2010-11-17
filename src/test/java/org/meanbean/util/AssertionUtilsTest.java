package org.meanbean.util;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AssertionUtilsTest {

	private static final String FAIL_MESSAGE = "TEST_FAIL_MESSAGE";

	@Test(expected = AssertionError.class)
	public void failShouldThrowAssertionError() throws Exception {
		AssertionUtils.fail();
	}

	@Test
	public void failWithMessageShouldThrowAssertionError() throws Exception {
		AssertionError assertionError = null;
		try {
			AssertionUtils.fail(FAIL_MESSAGE);
		} catch (AssertionError error) {
			assertionError = error;
		}
		assertThat("fail should have thrown AssertionError.", assertionError, is(not(nullValue())));
		assertThat("Incorrect message.", assertionError.getMessage(), is(FAIL_MESSAGE));
	}
}