package org.meanbean.factories.collections;

import java.util.ArrayList;
import java.util.List;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class ArrayListFactoryTest extends ListFactoryTestBase {

	@Override
    protected ListFactoryBase<String> getListFactory(RandomValueGenerator randomValueGenerator, Factory<String> itemFactory) {
	    return new ArrayListFactory<String>(randomValueGenerator, itemFactory);
    }

	@Override
    protected List<String> getListOfExpectedType() {
	    return new ArrayList<String>();
    }
}