package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class ShortFactoryTest extends BasicFactoryTestBase<Short> {

	@Override
	protected Factory<Short> createFactory(RandomValueGenerator randomValueGenerator) {
		return new ShortFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		boolean basedOnMaximum = true;
		boolean positive = true;
		return new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { 0.4532, 0.8765 },
		        new boolean[] { basedOnMaximum, positive, basedOnMaximum, positive });
	}

	private RandomValueGenerator createRandomNumberGenerator(boolean positive, boolean basedOnMaximum,
	        double randomNumber) {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { randomNumber },
		                new boolean[] { basedOnMaximum, positive });
		return randomValueGenerator;
	}

	@Test
	public void createShouldReturnPositiveShortBasedOnMaximumValue() throws Exception {
		double randomNumber = 0.6;
		ShortFactory factory = new ShortFactory(createRandomNumberGenerator(true, true, randomNumber));
		Short number = factory.create();
		Short expectedNumber = (short) (Short.MAX_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeShortBasedOnMaximumValue() throws Exception {
		double randomNumber = 0.2;
		ShortFactory factory = new ShortFactory(createRandomNumberGenerator(false, true, randomNumber));
		Short number = factory.create();
		Short expectedNumber = (short) (Short.MAX_VALUE * -randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnPositiveShortBasedOnMinimumValue() throws Exception {
		double randomNumber = 0.33;
		ShortFactory factory = new ShortFactory(createRandomNumberGenerator(true, false, randomNumber));
		Short number = factory.create();
		Short expectedNumber = (short) (Short.MIN_VALUE * randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}

	@Test
	public void createShouldReturnNegativeShortBasedOnMinimumValue() throws Exception {
		double randomNumber = 0.756;
		ShortFactory factory = new ShortFactory(createRandomNumberGenerator(false, false, randomNumber));
		Short number = factory.create();
		Short expectedNumber = (short) (Short.MIN_VALUE * -randomNumber);
		assertThat("Incorrect random number.", number, is(expectedNumber));
	}
}