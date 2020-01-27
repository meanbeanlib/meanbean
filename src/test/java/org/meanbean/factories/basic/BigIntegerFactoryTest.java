package org.meanbean.factories.basic;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.SimpleRandomValueGenerator;

import java.math.BigInteger;

public class BigIntegerFactoryTest extends BasicFactoryTestBase<BigInteger> {

	@Override
	protected RandomValueGenerator createRandomNumberGenerator() {
		return new SimpleRandomValueGenerator();
	}

	@Override
	protected Factory<BigInteger> createFactory(RandomValueGenerator randomValueGenerator) {
		return new BigIntegerFactory(randomValueGenerator);
	}

}