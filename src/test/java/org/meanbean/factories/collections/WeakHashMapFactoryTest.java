package org.meanbean.factories.collections;

import java.util.Map;
import java.util.WeakHashMap;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class WeakHashMapFactoryTest extends MapFactoryTestBase {

	@Override
	protected MapFactoryBase<String, Long> getMapFactory(RandomValueGenerator randomValueGenerator, Factory<String> keyFactory, Factory<Long> valueFactory) {
		return new WeakHashMapFactory<String, Long>(randomValueGenerator, keyFactory, valueFactory);
	}

	@Override
    protected Map<String, Long> getMapOfExpectedType() {
	    return new WeakHashMap<String, Long>();
    }
}