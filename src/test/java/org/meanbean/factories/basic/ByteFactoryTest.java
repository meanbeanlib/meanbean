package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class ByteFactoryTest extends BasicFactoryTestBase<Byte> {

	private static final byte RANDOM_BYTE_1 = -17;

	private static final byte RANDOM_BYTE_2 = 97;

	@Override
	protected Factory<Byte> createFactory(RandomNumberGenerator randomNumberGenerator) {
		return new ByteFactory(randomNumberGenerator);
	}

	@Override
	protected RandomNumberGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomNumberGenerator(new byte[][] { { RANDOM_BYTE_1 }, { RANDOM_BYTE_2 } }, null, null,
		        null, null, null);
	}

	@Test
	public void createShouldReturnExpectedBytes() throws Exception {
		Factory<Byte> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Byte.", factory.create(), is(RANDOM_BYTE_1));
		assertThat("Incorrect random Byte.", factory.create(), is(RANDOM_BYTE_2));
	}
}