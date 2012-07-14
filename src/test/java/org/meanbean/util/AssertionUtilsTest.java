package org.meanbean.util;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AssertionUtilsTest {

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionError() throws Exception {
        // Given
        // When
        AssertionUtils.fail();
        // Then - should throw AssertionError
    }

    @Test
    public void shouldThrowAssertionErrorWithMessage() throws Exception {
        // Given
        final String message = "TEST_FAIL_MESSAGE";
        // When
        AssertionError assertionError = null;
        try {
            AssertionUtils.fail(message);
        } catch (AssertionError error) {
            assertionError = error;
        }
        // Then
        assertThat("fail should have thrown AssertionError.", assertionError, is(not(nullValue())));
        assertThat("Incorrect message.", assertionError.getMessage(), is(message));
    }
}