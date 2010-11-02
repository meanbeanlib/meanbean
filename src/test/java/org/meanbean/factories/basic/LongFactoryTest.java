package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class LongFactoryTest extends BasicFactoryTestBase<Long> {
	
	private static long RANDOM_LONG_1 = 372036854775807L;

	private static long RANDOM_LONG_2 = -12320685475807L;
	
	@Override
	protected Factory<Long> createFactory(RandomNumberGenerator randomNumberGenerator) {
		return new LongFactory(randomNumberGenerator);
	}

	@Override
	protected RandomNumberGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomNumberGenerator(null, null, new long[] { RANDOM_LONG_1, RANDOM_LONG_2 }, null, null,
		        null);
	}

	@Test
	public void createShouldReturnExpectedLongs() throws Exception {
		Factory<Long> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Long.", factory.create(), is(RANDOM_LONG_1));
		assertThat("Incorrect random Long.", factory.create(), is(RANDOM_LONG_2));
	}
}