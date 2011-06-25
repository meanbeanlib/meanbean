package org.meanbean.factories.collections;

import java.util.HashSet;
import java.util.Set;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class HashSetFactoryTest extends SetFactoryTestBase {

	@Override
	protected SetFactoryBase<String> getSetFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory) {
		return new HashSetFactory<String>(randomValueGenerator, itemFactory);
	}

	@Override
	protected Set<String> getSetOfExpectedType() {
		return new HashSet<String>();
	}
}