package org.meanbean.bean.info;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class BeanInformationExceptionTest {

    private static final String EXPECTED_MESSAGE = "TEST_MESSAGE";

    @Test
    public void shouldHaveMessage() throws Exception {
        // When
        BeanInformationException exception = new BeanInformationException(EXPECTED_MESSAGE);
        // Then
        assertThat("Message was not set on exception.", exception.getMessage(), is(EXPECTED_MESSAGE));
    }

    @Test
    public void shouldHaveMessageAndCause() throws Exception {
        // Given
        final Throwable expectedCause = new IllegalArgumentException("ILLEGAL ARGUMENT EXCEPTION MESSAGE");
        // When
        BeanInformationException exception = new BeanInformationException(EXPECTED_MESSAGE, expectedCause);
        // Then
        assertThat("Unexpected message in exception.", exception.getMessage(), is(EXPECTED_MESSAGE));
        assertThat("Unexpected cause in exception.", exception.getCause(), is(expectedCause));
    }
}