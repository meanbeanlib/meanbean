package org.meanbean.bean.factory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class BeanCreationExceptionTest {

	private static final String MESSAGE = "TEST_MESSAGE";

	private static final Throwable CAUSE = new IllegalArgumentException("ILLEGAL ARGUMENT EXCEPTION MESSAGE");

	@Test
	public void constructWithMessage() throws Exception {
		BeanCreationException exception = new BeanCreationException(MESSAGE);
		assertThat("Message was not set on exception.", exception.getMessage(), is(MESSAGE));
	}

	@Test
	public void constructWithMessageAndCause() throws Exception {
		BeanCreationException exception = new BeanCreationException(MESSAGE, CAUSE);
		assertThat("Unexpected message in exception.", exception.getMessage(), is(MESSAGE));
		assertThat("Unexpected cause in exception.", exception.getCause(), is(CAUSE));
	}
}