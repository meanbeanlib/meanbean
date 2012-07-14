package org.meanbean.factories.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SimpleFactoryIdGeneratorTest {

    private final SimpleFactoryIdGenerator simpleFactoryIdGenerator = new SimpleFactoryIdGenerator();

    @Test(expected = IllegalArgumentException.class)
    public void shouldPreventIllegalClassParameter() {
        // Given
        Class<?> nullClass = null;
        // When
        simpleFactoryIdGenerator.createIdFromClass(nullClass);
        // Then - throw IllegalArgumentException
    }

    @Test
    public void shouldCreateIdForClass() {
        shouldCreateIdForClass(String.class, String.class.getName());
        shouldCreateIdForClass(Long.class, Long.class.getName());
        shouldCreateIdForClass(int.class, int.class.getName());
    }

    private void shouldCreateIdForClass(Class<?> inputClass, String expectedId) {
        // Given - input parameters
        // When
        String idFromClass = simpleFactoryIdGenerator.createIdFromClass(inputClass);
        // Then
        assertThat("Incorrect ID created.", idFromClass, is(expectedId));
    }
}