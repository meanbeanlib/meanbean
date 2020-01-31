package org.meanbean.factories.basic;

import org.junit.Test;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class RandomFactoryBaseTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullRandomNumberGenerator() throws Exception {
		new SimpleFactory<String>(null);
	}

	@Test
	public void getRandomNumberGeneratorShouldReturnRandomNumberGenerator() throws Exception {
		RandomValueGenerator randomValueGenerator = new SimpleRandomValueGenerator();
		SimpleFactory<String> factory = new SimpleFactory<String>(randomValueGenerator);
		assertThat("RandomNumberGenerator should not be null.", factory.getRandomValueGenerator(), is(not(nullValue())));
	}

	static class SimpleFactory<T> extends RandomFactoryBase<T> {

		public SimpleFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
			super(randomValueGenerator);
		}

		@Override
		public T create() {
			return null; // Not relevant in testing the base class - do nothing
		}
	}

}