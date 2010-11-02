package org.meanbean.factories;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class NoSuchFactoryExceptionTest {

	private static final String MESSAGE = "TEST_MESSAGE";
	
	private static final Throwable CAUSE = new IllegalArgumentException("ILLEGAL ARGUMENT EXCEPTION MESSAGE");

	@Test
    public void constructWithMessage() throws Exception {
		NoSuchFactoryException exception = new NoSuchFactoryException(MESSAGE);
	    assertThat("Message was not set on exception.", exception.getMessage(), is(MESSAGE));
	}
	
	@Test
    public void constructWithMessageAndCause() throws Exception {
		NoSuchFactoryException exception = new NoSuchFactoryException(MESSAGE, CAUSE);
	    assertThat("Unexpected message in exception.", exception.getMessage(), is(MESSAGE));
	    assertThat("Unexpected cause in exception.", exception.getCause(), is(CAUSE));
    }	
}