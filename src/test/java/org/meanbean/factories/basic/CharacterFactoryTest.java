package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class CharacterFactoryTest extends BasicFactoryTestBase<Character> {

	private static final double RANDOM_DOUBLE_1 = 0.45;

	private static final double RANDOM_DOUBLE_2 = 0.9345;

	@Override
	protected Factory<Character> createFactory(RandomNumberGenerator randomNumberGenerator) {
		return new CharacterFactory(randomNumberGenerator);
	}

	@Override
	protected RandomNumberGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomNumberGenerator(null, null, null, null, new double[] { RANDOM_DOUBLE_1,
		        RANDOM_DOUBLE_2 }, null);
	}

	@Test
	public void createShouldReturnCharactersMatchingRandomIntegers() throws Exception {
		Factory<Character> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random character.", factory.create().charValue(),
		        is((char) (Character.MAX_VALUE * RANDOM_DOUBLE_1)));
		assertThat("Incorrect random character.", factory.create().charValue(),
		        is((char) (Character.MAX_VALUE * RANDOM_DOUBLE_2)));
	}
}