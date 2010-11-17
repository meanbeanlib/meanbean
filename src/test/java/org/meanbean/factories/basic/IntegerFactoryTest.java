package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class IntegerFactoryTest extends BasicFactoryTestBase<Integer> {

	private static final int RANDOM_INT_1 = -2147483567;

	private static final int RANDOM_INT_2 = 2143793567;

	@Override
	protected Factory<Integer> createFactory(RandomValueGenerator randomValueGenerator) {
		return new IntegerFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, new int[] { RANDOM_INT_1, RANDOM_INT_2 }, null, null, null,
		        null);
	}

	@Test
	public void createShouldReturnExpectedIntegers() throws Exception {
		Factory<Integer> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Integer.", factory.create(), is(RANDOM_INT_1));
		assertThat("Incorrect random Integer.", factory.create(), is(RANDOM_INT_2));
	}
}