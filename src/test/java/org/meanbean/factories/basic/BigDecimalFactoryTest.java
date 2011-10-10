package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class BigDecimalFactoryTest extends BasicFactoryTestBase<BigDecimal> {

	private static final long RANDOM_LONG_1 = -21474835679L;

	private static final long RANDOM_LONG_2 = 21437935679L;

	@Override
	protected Factory<BigDecimal> createFactory(RandomValueGenerator randomValueGenerator) {
		return new BigDecimalFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, new long[] { RANDOM_LONG_1, RANDOM_LONG_2 }, null, null,
		        null);
	}

	@Test
	public void createShouldReturnExpectedBigDecimals() throws Exception {
		Factory<BigDecimal> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random BigDecimal.", factory.create(), is(new BigDecimal(RANDOM_LONG_1)));
		assertThat("Incorrect random BigDecimal.", factory.create(), is(new BigDecimal(RANDOM_LONG_2)));
	}
}