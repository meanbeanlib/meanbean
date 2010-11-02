package org.meanbean.test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class BeanTestExceptionTest {

	private static final String MESSAGE = "TEST_MESSAGE";
	
	private static final Throwable CAUSE = new IllegalArgumentException("ILLEGAL ARGUMENT EXCEPTION MESSAGE");

	@Test
    public void constructWithMessage() throws Exception {
	    BeanTestException exception = new BeanTestException(MESSAGE);
	    assertThat("Message was not set on exception.", exception.getMessage(), is(MESSAGE));
	}
	
	@Test
    public void constructWithMessageAndCause() throws Exception {
		BeanTestException exception = new BeanTestException(MESSAGE, CAUSE);
	    assertThat("Unexpected message in exception.", exception.getMessage(), is(MESSAGE));
	    assertThat("Unexpected cause in exception.", exception.getCause(), is(CAUSE));
	    
    }	
}