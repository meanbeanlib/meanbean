package org.meanbean.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class SimpleRandomValueGeneratorTest {

    private static final int ITERATIONS = 10000;

    private RandomValueGenerator randomValueGenerator;

    @Before
    public void before() throws Exception {
        randomValueGenerator = new SimpleRandomValueGenerator();
    }

    @Test
    public void nextByteShouldGeneratePositiveAndNegativeNumbers() {
        // Given
        int totalPositive = 0;
        int totalNegative = 0;
        // When
        for (int idx = 0; idx < ITERATIONS; idx++) {
            byte next = randomValueGenerator.nextByte();
            if (next < 0) {
                totalNegative++;
            } else {
                totalPositive++;
            }
        }
        // Then
        assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
        assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
    }

    @Test
    public void nextBytesShouldGenerateArrayOfRequestedSize() {
        // Given
        final int size = 100;
        // When
        byte[] generatedBytes = randomValueGenerator.nextBytes(size);
        // Then
        assertThat("should generate requested size array.", generatedBytes.length, is(size));
    }

    @Test
    public void nextBytesShouldPermitGenerationOfArrayOfSizeZero() {
        // Given
        final int size = 0;
        // When
        byte[] generatedBytes = randomValueGenerator.nextBytes(size);
        // Then
        assertThat("should generate requested size array.", generatedBytes.length, is(size));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nextBytesShouldPreventNegativeSize() {
        // Given
        final int negativeSize = -1;
        // When
        randomValueGenerator.nextBytes(negativeSize);
        // Then - throws IllegalArgumentException
    }

    @Test
    public void nextBytesShouldGeneratePositiveAndNegativeNumbers() {
        // Given
        final int size = 100;
        // When
        byte[] bytes = randomValueGenerator.nextBytes(size);
        // Then
        assertThat("should generate requested size array.", bytes.length, is(size));
        int totalPositive = 0;
        int totalNegative = 0;
        for (byte bite : bytes) {
            if (bite < 0) {
                totalNegative++;
            } else {
                totalPositive++;
            }
        }
        assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
        assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
    }

    @Test
    public void nextIntShouldGeneratePositiveAndNegativeNumbers() {
        // Given
        int totalPositive = 0;
        int totalNegative = 0;
        // When
        for (int idx = 0; idx < ITERATIONS; idx++) {
            int next = randomValueGenerator.nextInt();
            if (next < 0) {
                totalNegative++;
            } else {
                totalPositive++;
            }
        }
        // Then
        assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
        assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
    }

    @Test
    public void nextLongShouldGeneratePositiveAndNegativeNumbers() {
        // Given
        int totalPositive = 0;
        int totalNegative = 0;
        // When
        for (int idx = 0; idx < ITERATIONS; idx++) {
            long next = randomValueGenerator.nextLong();
            if (next < 0) {
                totalNegative++;
            } else {
                totalPositive++;
            }
        }
        // Then
        assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
        assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
    }

    @Test
    public void nextBooleanShouldGenerateTrueAndFalseValues() {
        // Given
        int totalTrue = 0;
        int totalFalse = 0;
        // When
        for (int idx = 0; idx < ITERATIONS; idx++) {
            boolean next = randomValueGenerator.nextBoolean();
            if (next) {
                totalTrue++;
            } else {
                totalFalse++;
            }
        }
        // Then
        assertThat("should generate trues.", totalTrue, is(greaterThan(0)));
        assertThat("should generate falses.", totalFalse, is(greaterThan(0)));
    }

    @Test
    public void nextFloatShouldGenerateNumbersOnlyWithinZeroInclusiveAndOneExclusive() {
        // Given
        int totalOutsideRange = 0;
        // When
        for (int idx = 0; idx < ITERATIONS; idx++) {
            float next = randomValueGenerator.nextFloat();
            if ((next < 0f) || (next >= 1f)) {
                totalOutsideRange++;
            }
        }
        // Then
        assertThat("should generate floats between 0.0 (incl) and 1.0 (excl).", totalOutsideRange, is(0));
    }

    @Test
    public void nextDoubleShouldGenerateNumbersOnlyWithinZeroInclusiveAndOneExclusive() {
        // Given
        int totalOutsideRange = 0;
        // When
        for (int idx = 0; idx < ITERATIONS; idx++) {
            double next = randomValueGenerator.nextDouble();
            if ((next < 0d) || (next >= 1d)) {
                totalOutsideRange++;
            }
        }
        // Then
        assertThat("should generate doubles between 0.0 (incl) and 1.0 (excl).", totalOutsideRange, is(0));
    }
}