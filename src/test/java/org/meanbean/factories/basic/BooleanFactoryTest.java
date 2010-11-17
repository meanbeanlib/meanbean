package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class BooleanFactoryTest extends BasicFactoryTestBase<Boolean> {

	private static final Boolean RANDOM_BOOLEAN_1 = false;

	private static final Boolean RANDOM_BOOLEAN_2 = true;

	@Override
	protected Factory<Boolean> createFactory(RandomValueGenerator randomValueGenerator) {
		return new BooleanFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, null, null, null, new boolean[] { RANDOM_BOOLEAN_1,
		        RANDOM_BOOLEAN_2 });
	}

	@Test
	public void createShouldReturnExpectedBooleans() throws Exception {
		Factory<Boolean> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Boolean.", factory.create(), is(RANDOM_BOOLEAN_1));
		assertThat("Incorrect random Boolean.", factory.create(), is(RANDOM_BOOLEAN_2));
	}
}