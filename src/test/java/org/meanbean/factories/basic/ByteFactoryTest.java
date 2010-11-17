package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class ByteFactoryTest extends BasicFactoryTestBase<Byte> {

	private static final byte RANDOM_BYTE_1 = -17;

	private static final byte RANDOM_BYTE_2 = 97;

	@Override
	protected Factory<Byte> createFactory(RandomValueGenerator randomValueGenerator) {
		return new ByteFactory(randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(new byte[][] { { RANDOM_BYTE_1 }, { RANDOM_BYTE_2 } }, null, null,
		        null, null, null);
	}

	@Test
	public void createShouldReturnExpectedBytes() throws Exception {
		Factory<Byte> factory = createFactory(createRandomNumberGenerator());
		assertThat("Incorrect random Byte.", factory.create(), is(RANDOM_BYTE_1));
		assertThat("Incorrect random Byte.", factory.create(), is(RANDOM_BYTE_2));
	}
}