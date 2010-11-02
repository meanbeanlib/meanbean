package org.meanbean.factories.collections;

import java.util.HashSet;
import java.util.Set;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class HashSetFactoryTest extends SetFactoryTestBase {

	@Override
    protected SetFactoryBase<String> getSetFactory(RandomNumberGenerator randomNumberGenerator, Factory<String> itemFactory) {
	    return new HashSetFactory<String>(randomNumberGenerator, itemFactory);
    }

	@Override
    protected Set<String> getSetOfExpectedType() {
	    return new HashSet<String>();
    }
}