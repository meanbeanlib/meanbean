package org.meanbean.factories.collections;

import java.util.IdentityHashMap;
import java.util.Map;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class IdentityHashMapFactoryTest extends MapFactoryTestBase {

	@Override
	protected MapFactoryBase<String, Long> getMapFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> keyFactory, Factory<Long> valueFactory) {
		return new IdentityHashMapFactory<String, Long>(randomValueGenerator, keyFactory, valueFactory);
	}

	@Override
	protected Map<String, Long> getMapOfExpectedType() {
		return new IdentityHashMap<String, Long>();
	}
}