package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class DateFactoryTest extends BasicFactoryTestBase<Date> {

	private static long RANDOM_LONG_1 = 372036854775807L;

	private static long RANDOM_LONG_2 = -12320685475807L;

	@Override
	protected Factory<Date> createFactory(RandomValueGenerator randomValueGenerator) {
		return new DateFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, new long[] { RANDOM_LONG_1, RANDOM_LONG_2 }, null, null,
		        null);
	}

	@Test
	public void createShouldReturnDateWithTimeInMillisMatchingRandomLong() throws Exception {
		Factory<Date> factory =
		        createFactory(new ArrayBasedRandomValueGenerator(null, null, new long[] { RANDOM_LONG_1 }, null, null,
		                null));
		assertThat("Incorrect random date.", factory.create().getTime(), is(RANDOM_LONG_1));
	}

	@Test
	public void createShouldReturnDateWithTimeInMillisMatchingAbsoluteRandomLong() throws Exception {
		Factory<Date> factory =
		        createFactory(new ArrayBasedRandomValueGenerator(null, null, new long[] { RANDOM_LONG_2 }, null, null,
		                null));
		assertThat("Incorrect random date.", factory.create().getTime(), is(Math.abs(RANDOM_LONG_2)));
	}
}