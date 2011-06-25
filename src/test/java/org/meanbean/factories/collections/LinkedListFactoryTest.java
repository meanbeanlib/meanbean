package org.meanbean.factories.collections;

import java.util.LinkedList;
import java.util.List;

import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public class LinkedListFactoryTest extends ListFactoryTestBase {

	@Override
	protected ListFactoryBase<String> getListFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory) {
		return new LinkedListFactory<String>(randomValueGenerator, itemFactory);
	}

	@Override
	protected List<String> getListOfExpectedType() {
		return new LinkedList<String>();
	}
}