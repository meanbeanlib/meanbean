package org.meanbean.factories;

import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.beans.CollectionPropertyBean;

public class CollectionFactoryCollectionTest {

	@Test
	public void test() throws Exception {
		BeanTester tester = new BeanTester();
		tester.testBean(CollectionPropertyBean.class);
	}
}
