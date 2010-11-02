package org.meanbean.factories.collections;

import java.util.Set;
import java.util.TreeSet;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class TreeSetFactoryTest extends SetFactoryTestBase {

	@Override
    protected SetFactoryBase<String> getSetFactory(RandomNumberGenerator randomNumberGenerator, Factory<String> itemFactory) {
		return new TreeSetFactory<String>(randomNumberGenerator, itemFactory);
    }

	@Override
    protected Set<String> getSetOfExpectedType() {
	    return new TreeSet<String>();
    }
}