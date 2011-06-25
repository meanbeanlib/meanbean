package org.meanbean.factories.collections;

import java.util.LinkedHashSet;
import java.util.Set;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class LinkedHashSetFactoryTest extends SetFactoryTestBase {

	@Override
	protected SetFactoryBase<String> getSetFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory) {
		return new LinkedHashSetFactory<String>(randomValueGenerator, itemFactory);
	}

	@Override
	protected Set<String> getSetOfExpectedType() {
		return new LinkedHashSet<String>();
	}
}