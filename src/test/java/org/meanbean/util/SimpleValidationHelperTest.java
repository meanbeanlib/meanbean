package org.meanbean.util;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SimpleValidationHelperTest {

    private static final String NAME = "TEST_NAME";

    private static final String OPERATION = "TEST_OPERATION";

    private static final Object VALID_VALUE = "TEST_VALUE";

    private static final Object NULL_VALUE = null;

    private final SimpleValidationHelper simpleValidationHelper = new SimpleValidationHelper();

    @Test
    public void shouldPreventInvalidValueWhenNameAndValueProvided() {
        // Given
        Exception expectedException = new IllegalArgumentException("Object [" + NAME + "] must be provided.");
        // When
        Exception actualException = null;
        try {
            simpleValidationHelper.ensureExists(NAME, NULL_VALUE);
        } catch (Exception exception) {
            actualException = exception;
        }
        // Then
        assertIsExpectedException(expectedException, actualException);
    }

    @Test
    public void shouldPreventInvalidValueWhenNameAndOperationAndValueProvided() {
        // Given
        Exception expectedException = new IllegalArgumentException("Cannot " + OPERATION + " with null " + NAME + ".");
        // When
        Exception actualException = null;
        try {
            simpleValidationHelper.ensureExists(NAME, OPERATION, NULL_VALUE);
        } catch (Exception exception) {
            actualException = exception;
        }
        // Then
        assertIsExpectedException(expectedException, actualException);
    }

    private void assertIsExpectedException(Exception expectedException, Exception actualException) {
        assertThat("Expected exception not thrown.", actualException, is(instanceOf(expectedException.getClass())));
        assertThat("Expected exception message not present.", actualException.getMessage(),
                is(expectedException.getMessage()));
    }

    @Test
    public void ensureExistsWithValidNameAndValue() {
        // Given
        // When
        simpleValidationHelper.ensureExists(NAME, VALID_VALUE);
        // Then - no Exception is thrown
    }

    @Test
    public void ensureExistsWithValidNameAndValueAndOperation() {
        // Given
        // When
        simpleValidationHelper.ensureExists(NAME, OPERATION, VALID_VALUE);
        // Then - no Exception is thrown
    }
}