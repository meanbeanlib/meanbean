package org.meanbean.factories.collections;

import java.util.LinkedHashSet;
import java.util.Set;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class LinkedHashSetFactoryTest extends SetFactoryTestBase {

	@Override
    protected SetFactoryBase<String> getSetFactory(RandomNumberGenerator randomNumberGenerator, Factory<String> itemFactory) {
		return new LinkedHashSetFactory<String>(randomNumberGenerator, itemFactory);
    }

	@Override
    protected Set<String> getSetOfExpectedType() {
	    return new LinkedHashSet<String>();
    }
}