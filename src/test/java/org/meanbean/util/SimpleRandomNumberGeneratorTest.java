package org.meanbean.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class SimpleRandomNumberGeneratorTest {

	private static final int ITERATIONS = 10000;

	private RandomNumberGenerator randomNumberGenerator;

	@Before
	public void before() throws Exception {
		randomNumberGenerator = new SimpleRandomNumberGenerator();
	}

	@Test
	public void nextByteShouldGeneratePositiveAndNegativeNumbers() {
		int totalPositive = 0;
		int totalNegative = 0;
		for (int idx = 0; idx < ITERATIONS; idx++) {
			byte next = randomNumberGenerator.nextByte();
			if (next < 0) {
				totalNegative++;
			} else {
				totalPositive++;
			}
		}
		assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
		assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
	}

	@Test
	public void nextBytesShouldGenerateArrayOfRequestedSize() {
		final int size = 100;
		assertThat("should generate requested size array.", randomNumberGenerator.nextBytes(size).length, is(size));
	}

	@Test
	public void nextBytesShouldPermitGenerationOfArrayOfSizeZero() {
		final int size = 0;
		assertThat("should generate requested size array.", randomNumberGenerator.nextBytes(size).length, is(size));
	}

	@Test(expected = IllegalArgumentException.class)
	public void nextBytesShouldPreventNegativeSize() {
		randomNumberGenerator.nextBytes(-1);
	}

	@Test
	public void nextBytesShouldGeneratePositiveAndNegativeNumbers() {
		final int size = 100;
		byte[] bytes = randomNumberGenerator.nextBytes(size);
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
		int totalPositive = 0;
		int totalNegative = 0;
		for (int idx = 0; idx < ITERATIONS; idx++) {
			int next = randomNumberGenerator.nextInt();
			if (next < 0) {
				totalNegative++;
			} else {
				totalPositive++;
			}
		}
		assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
		assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
	}

	@Test
	public void nextLongShouldGeneratePositiveAndNegativeNumbers() {
		int totalPositive = 0;
		int totalNegative = 0;
		for (int idx = 0; idx < ITERATIONS; idx++) {
			long next = randomNumberGenerator.nextLong();
			if (next < 0) {
				totalNegative++;
			} else {
				totalPositive++;
			}
		}
		assertThat("should generate positive numbers.", totalPositive, is(greaterThan(0)));
		assertThat("should generate negative numbers.", totalNegative, is(greaterThan(0)));
	}

	@Test
	public void nextBooleanShouldGenerateTrueAndFalseValues() {
		int totalTrue = 0;
		int totalFalse = 0;
		for (int idx = 0; idx < ITERATIONS; idx++) {
			boolean next = randomNumberGenerator.nextBoolean();
			if (next) {
				totalTrue++;
			} else {
				totalFalse++;
			}
		}
		assertThat("should generate trues.", totalTrue, is(greaterThan(0)));
		assertThat("should generate falses.", totalFalse, is(greaterThan(0)));
	}

	@Test
	public void nextFloatShouldGenerateNumbersOnlyWithinZeroInclusiveAndOneExclusive() {
		int totalOutsideRange = 0;
		for (int idx = 0; idx < ITERATIONS; idx++) {
			float next = randomNumberGenerator.nextFloat();
			if ((next < 0f) || (next >= 1f)) {
				totalOutsideRange++;
			}
		}
		assertThat("should generate floats between 0.0 (incl) and 1.0 (excl).", totalOutsideRange, is(0));
	}

	@Test
	public void nextDoubleShouldGenerateNumbersOnlyWithinZeroInclusiveAndOneExclusive() {
		int totalOutsideRange = 0;
		for (int idx = 0; idx < ITERATIONS; idx++) {
			double next = randomNumberGenerator.nextDouble();
			if ((next < 0d) || (next >= 1d)) {
				totalOutsideRange++;
			}
		}
		assertThat("should generate doubles between 0.0 (incl) and 1.0 (excl).", totalOutsideRange, is(0));
	}
}