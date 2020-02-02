package org.meanbean.factories;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class ArrayFactoryCollectionTest {

	private ArrayFactoryCollection arrayFactoryCollection = new ArrayFactoryCollection();

	@Test
	public void hasFactory() throws Exception {
		assertThat(arrayFactoryCollection.hasFactory(UUID[][].class))
				.isEqualTo(true);
	}

	@Test
	public void getFactory() throws Exception {
		@SuppressWarnings("unchecked")
		Factory<UUID[][]> factory = (Factory<UUID[][]>) arrayFactoryCollection.getFactory(UUID[][].class);
		UUID[][] matrix = factory.create();

		assertThat(matrix)
				.hasSizeLessThanOrEqualTo(arrayFactoryCollection.getMaxArrayLength());
		for (UUID[] array : matrix) {
			assertThat(array)
					.hasSizeLessThanOrEqualTo(arrayFactoryCollection.getMaxArrayLength());
		}
	}

	@Test
	public void maxArrayLength() {
		assertThat(arrayFactoryCollection.getMaxArrayLength())
				.isLessThanOrEqualTo(8);
	}

	@Test
	public void noBeanTesterExceptionForArrayBean() {
		assertThatCode(() -> {
			new BeanTester().testBean(ArrayBean.class);
		}).doesNotThrowAnyException();
	}

	public static class ArrayBean {
		private String[] values;

		public String[] getValues() {
			return values;
		}

		public void setValues(String[] values) {
			this.values = values;
		}

	}
}
