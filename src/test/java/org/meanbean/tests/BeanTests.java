package org.meanbean.tests;

import org.meanbean.beans.Gender;
import org.meanbean.beans.PersonBean;
import org.meanbean.beans.factories.GenderFactory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.BeanTesterImpl;
import org.meanbean.test.ConfigurationBuilder;

public class BeanTests {

	public void testBeans() {
		BeanTester beanTester = new BeanTesterImpl();
		beanTester.getFactoryCollection().addFactory(Gender.class,
		        new GenderFactory(beanTester.getRandomValueGenerator()));
		beanTester.setIterations(1);
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder().ignoreProperty("address");
		// configurationBuilder.addIgnoredProperty("otherFavouriteNumbers");
		beanTester.addCustomConfiguration(PersonBean.class, configurationBuilder.build());
		beanTester.testBean(PersonBean.class);
		System.out.println("INFO: Testing completed!");
	}

	public static void main(String[] args) {
		BeanTests beanTests = new BeanTests();
		beanTests.testBeans();
	}

}
