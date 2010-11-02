package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class StringFactoryTest extends BasicFactoryTestBase<String> {

	private static final long RANDOM_LONG_1 = 841027364;

	private static final long RANDOM_LONG_2 = -5928352;

	@Override
	protected Factory<String> createFactory(RandomNumberGenerator randomNumberGenerator) {
		return new StringFactory(randomNumberGenerator);
	}

	@Override
	protected RandomNumberGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomNumberGenerator(null, null, new long[] { RANDOM_LONG_1, RANDOM_LONG_2 }, null, null,
		        null);
	}

	@Test
	public void createShouldReturnExpectedStrings() throws Exception {
		Factory<String> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random String.", factory.create(), is("TestString:[" + RANDOM_LONG_1 + "]"));
		assertThat("Incorrect random String.", factory.create(), is("TestString:[" + RANDOM_LONG_2 + "]"));
	}
}