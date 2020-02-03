package org.meanbean.factories.collections;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.beans.domain.Customer;
import org.meanbean.util.RandomValueGenerator;

import java.util.HashSet;
import java.util.Set;

public class HashSetFactoryTest extends SetFactoryTestBase {

	@Override
	protected SetFactoryBase<String> getSetFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory) {
		return new HashSetFactory<String>(randomValueGenerator, itemFactory);
	}

	@Override
	protected Set<String> getSetOfExpectedType() {
		return new HashSet<String>();
	}
	
	@Test
	public void set() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(Customer.class);
	}
}