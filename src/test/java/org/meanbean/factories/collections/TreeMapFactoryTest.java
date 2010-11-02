package org.meanbean.factories.collections;

import java.util.Map;
import java.util.TreeMap;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class TreeMapFactoryTest extends MapFactoryTestBase {

	@Override
	protected MapFactoryBase<String, Long> getMapFactory(RandomNumberGenerator randomNumberGenerator, Factory<String> keyFactory, Factory<Long> valueFactory) {
		return new TreeMapFactory<String, Long>(randomNumberGenerator, keyFactory, valueFactory);
	}

	@Override
    protected Map<String, Long> getMapOfExpectedType() {
	    return new TreeMap<String, Long>();
    }
}