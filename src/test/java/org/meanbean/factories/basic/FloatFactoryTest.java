package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class FloatFactoryTest extends BasicFactoryTestBase<Float> {

	@Override
	protected Factory<Float> createFactory(RandomNumberGenerator randomNumberGenerator) {
		return new FloatFactory(randomNumberGenerator);
	}

	@Override
	protected RandomNumberGenerator createRandomNumberGenerator() {
		boolean basedOnMaximum = true;
		boolean positive = true;
		return new ArrayBasedRandomNumberGenerator(null, null, null, new float[] { 0.61f, 0.62f }, null, new boolean[] {
		        basedOnMaximum, positive, basedOnMaximum, positive });
	}

	private RandomNumberGenerator createRandomNumberGenerator(boolean positive, boolean basedOnMaximum,
	        float randomNumber) {
		RandomNumberGenerator randomNumberGenerator = new ArrayBasedRandomNumberGenerator(null, null, null,
		        new float[] { randomNumber }, null, new boolean[] { basedOnMaximum, positive });
		return randomNumberGenerator;
	}

	@Test
	public void createShouldReturnPositiveFloatBasedOnMaximumValue() throws Exception {
		float randomNumber = 0.6f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(true, true, randomNumber));
		Float number = factory.create();
		Float expectedNumber = Float.MAX_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeFloatBasedOnMaximumValue() throws Exception {
		float randomNumber = 0.2f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(false, true, randomNumber));
		Float number = factory.create();
		Float expectedNumber = -(Float.MAX_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnPositiveFloatBasedOnMinimumValue() throws Exception {
		float randomNumber = 0.33f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(true, false, randomNumber));
		Float number = factory.create();
		Float expectedNumber = Float.MIN_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeFloatBasedOnMinimumValue() throws Exception {
		float randomNumber = 0.756f;
		FloatFactory factory = new FloatFactory(createRandomNumberGenerator(false, false, randomNumber));
		Float number = factory.create();
		Float expectedNumber = -(Float.MIN_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}
}