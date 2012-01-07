package org.meanbean.test.beans;

import java.util.Calendar;
import java.util.Date;

import org.meanbean.lang.EquivalentFactory;

/**
 * Factory that creates logically equivalent ComplexBean instances. This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class ComplexBeanFactory implements EquivalentFactory<ComplexBean> {

	private static final Date DATE_OF_BIRTH;
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2010, 7, 12, 16, 31, 1);
		DATE_OF_BIRTH = calendar.getTime();
	}

	public ComplexBean create() {
		ComplexBean bean = new ComplexBean();
		bean.setId(1);
		bean.setFirstName("TEST_FIRST_NAME");
		bean.setLastName("TEST_LAST_NAME");
		bean.setDateOfBirth(DATE_OF_BIRTH);
		bean.setFavouriteNumber(1234);
		return bean;
	}
}
