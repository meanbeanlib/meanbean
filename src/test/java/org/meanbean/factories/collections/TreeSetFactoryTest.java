package org.meanbean.factories.collections;

import java.util.Set;
import java.util.TreeSet;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class TreeSetFactoryTest extends SetFactoryTestBase {

	@Override
	protected SetFactoryBase<String> getSetFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory) {
		return new TreeSetFactory<String>(randomValueGenerator, itemFactory);
	}

	@Override
	protected Set<String> getSetOfExpectedType() {
		return new TreeSet<String>();
	}
}