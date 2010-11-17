package org.meanbean.factories.basic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class EnumFactoryTest extends BasicFactoryTestBase<Enum<?>> {

	@Override
	protected Factory<Enum<?>> createFactory(RandomValueGenerator randomValueGenerator) {
		return new EnumFactory(Color.class, randomValueGenerator);
	}

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new ArrayBasedRandomValueGenerator(null, null, null, null, new double[] { 0.3, 0.6, 1 }, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullEnumClass() throws Exception {
		RandomValueGenerator randomValueGenerator = new ArrayBasedRandomValueGenerator(null, null, null, null, null,
		        null);
		new EnumFactory(null, randomValueGenerator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventIllegalEnumEnumClass() throws Exception {
		RandomValueGenerator randomValueGenerator = new ArrayBasedRandomValueGenerator(null, null, null, null, null,
		        null);
		new EnumFactory(String.class, randomValueGenerator);
	}

	@Test
	public void createShouldReturnEachEnum() throws Exception {
		double RED_DOUBLE = 1.0 / Color.values().length;
		double GREEN_DOUBLE = 2.0 / Color.values().length;
		double BLUE_DOUBLE = 3.0 / Color.values().length;
		RandomValueGenerator randomValueGenerator = new ArrayBasedRandomValueGenerator(null, null, null, null,
		        new double[] { RED_DOUBLE, GREEN_DOUBLE, BLUE_DOUBLE }, null);
		EnumFactory enumFactory = new EnumFactory(Color.class, randomValueGenerator);
		assertThat("Incorrect enum.", (Color) enumFactory.create(), is(Color.RED));
		assertThat("Incorrect enum.", (Color) enumFactory.create(), is(Color.GREEN));
		assertThat("Incorrect enum.", (Color) enumFactory.create(), is(Color.BLUE));
	}

	static enum Color {
		RED, GREEN, BLUE
	}
}