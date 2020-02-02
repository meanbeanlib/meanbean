package org.meanbean.util;

import org.junit.Test;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.bean.info.JavaBeanInformationFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceFactoryTest {

	@Test
	public void loadSingleImplementor() throws Exception {
		List<BeanInformationFactory> services = getAll();

		assertThat(services)
				.hasOnlyElementsOfType(JavaBeanInformationFactory.class)
				.hasSize(1);
	}

	@Test
	public void getInstanceCaches() throws Exception {
		List<BeanInformationFactory> services1 = getAll();
		List<BeanInformationFactory> services2 = getAll();

		assertThat(services1)
				.isSameAs(services2);
	}

	private List<BeanInformationFactory> getAll() {
		return BeanInformationFactory.getServiceDefinition()
				.getServiceFactory()
				.getAll();
	}

}