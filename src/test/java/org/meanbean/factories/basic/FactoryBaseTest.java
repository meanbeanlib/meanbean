package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;
import org.meanbean.util.RandomNumberGenerator;
import org.meanbean.util.SimpleRandomNumberGenerator;

public class FactoryBaseTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorShouldPreventNullRandomNumberGenerator() throws Exception {
		new SimpleFactory<String>(null);
	}

	@Test
	public void getRandomNumberGeneratorShouldReturnRandomNumberGenerator() throws Exception {
		RandomNumberGenerator randomNumberGenerator = new SimpleRandomNumberGenerator();
		SimpleFactory<String> factory = new SimpleFactory<String>(randomNumberGenerator);
		assertThat("RandomNumberGenerator should not be null.", factory.getRandomNumberGenerator(),
		        is(not(nullValue())));
	}

	static class SimpleFactory<T> extends FactoryBase<T> {

		private static final long serialVersionUID = 1L;

		public SimpleFactory(RandomNumberGenerator randomNumberGenerator) throws IllegalArgumentException {
			super(randomNumberGenerator);
		}

		@Override
		public T create() {
			return null; // Not relevant in testing the base class - do nothing
		}
	}

}