package org.meanbean.util;

import org.junit.Test;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceFactoryTest {

	@Test
	public void loadSingleImplementor() throws Exception {
		List<BeanInformationFactory> services = ServiceFactory.getInstance(BeanInformationFactory.class)
				.load();

		assertThat(services)
				.hasOnlyElementsOfType(JavaBeanInformationFactory.class)
				.hasSize(1);
	}

	@Test
	public void getInstanceCaches() throws Exception {
		List<BeanInformationFactory> services1 = ServiceFactory.getInstance(BeanInformationFactory.class)
				.load();
		List<BeanInformationFactory> services2 = ServiceFactory.getInstance(BeanInformationFactory.class)
				.load();

		List<BeanInformationFactory> services3 = ServiceFactory.newInstance(BeanInformationFactory.class)
				.load();

		assertThat(services1)
				.isSameAs(services2);

		assertThat(services1)
				.isNotSameAs(services3);
	}

}