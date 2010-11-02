package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class DoubleFactoryTest extends BasicFactoryTestBase<Double> {

	@Override
	protected Factory<Double> createFactory(RandomNumberGenerator randomNumberGenerator) {
		return new DoubleFactory(randomNumberGenerator);
	}

	@Override
	protected RandomNumberGenerator createRandomNumberGenerator() {
		boolean basedOnMaximum = true;
		boolean positive = true;
		return new ArrayBasedRandomNumberGenerator(null, null, null, null, new double[] { 0.61, 0.62 }, new boolean[] {
		        basedOnMaximum, positive, basedOnMaximum, positive });
	}

	private RandomNumberGenerator createRandomNumberGenerator(boolean positive, boolean basedOnMaximum,
	        double randomNumber) {
		RandomNumberGenerator randomNumberGenerator = new ArrayBasedRandomNumberGenerator(null, null, null, null,
		        new double[] { randomNumber }, new boolean[] { basedOnMaximum, positive });
		return randomNumberGenerator;
	}

	@Test
	public void createShouldReturnPositiveDoubleBasedOnMaximumValue() throws Exception {
		double randomNumber = 0.6;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(true, true, randomNumber));
		Double number = factory.create();
		Double expectedNumber = Double.MAX_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeDoubleBasedOnMaximumValue() throws Exception {
		double randomNumber = 0.2;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(false, true, randomNumber));
		Double number = factory.create();
		Double expectedNumber = -(Double.MAX_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnPositiveDoubleBasedOnMinimumValue() throws Exception {
		double randomNumber = 0.33;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(true, false, randomNumber));
		Double number = factory.create();
		Double expectedNumber = Double.MIN_VALUE * randomNumber;
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeDoubleBasedOnMinimumValue() throws Exception {
		double randomNumber = 0.756;
		DoubleFactory factory = new DoubleFactory(createRandomNumberGenerator(false, false, randomNumber));
		Double number = factory.create();
		Double expectedNumber = -(Double.MIN_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}
}