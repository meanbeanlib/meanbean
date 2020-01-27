package org.meanbean.factories.basic;

import org.meanbean.util.RandomValueGenerator;

import java.math.BigInteger;

public final class BigIntegerFactory extends RandomFactoryBase<BigInteger> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	public BigIntegerFactory(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		super(randomValueGenerator);
	}

	@Override
	public BigInteger create() {
		return BigInteger.valueOf(getRandomValueGenerator().nextInt());
	}
}