package org.meanbean.factories.collections;

import java.util.ArrayList;
import java.util.List;

import org.meanbean.factories.Factory;
import org.meanbean.util.RandomNumberGenerator;

public class ArrayListFactoryTest extends ListFactoryTestBase {

	@Override
    protected ListFactoryBase<String> getListFactory(RandomNumberGenerator randomNumberGenerator, Factory<String> itemFactory) {
	    return new ArrayListFactory<String>(randomNumberGenerator, itemFactory);
    }

	@Override
    protected List<String> getListOfExpectedType() {
	    return new ArrayList<String>();
    }
}