package org.meanbean.factories.collections;

import java.util.LinkedList;
import java.util.List;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class LinkedListFactoryTest extends ListFactoryTestBase {

	@Override
    protected ListFactoryBase<String> getListFactory(RandomNumberGenerator randomNumberGenerator, Factory<String> itemFactory) {
	    return new LinkedListFactory<String>(randomNumberGenerator, itemFactory);
    }

	@Override
    protected List<String> getListOfExpectedType() {
	    return new LinkedList<String>();
    }
}