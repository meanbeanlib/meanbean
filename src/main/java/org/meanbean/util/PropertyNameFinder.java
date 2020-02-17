package org.meanbean.util;

import com.github.meanbeanlib.mirror.Executables;
import com.github.meanbeanlib.mirror.SerializableLambdas.SerializableFunction1;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class PropertyNameFinder {

	public static <T, S> String findPropertyName(Class<T> beanClass, SerializableFunction1<T, S> beanGetter) {
		Method method = Executables.findGetter(beanGetter);
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor property = Stream.of(beanInfo.getPropertyDescriptors())
					.filter(pd -> method.equals(pd.getReadMethod()))
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Invalid bean getter method:" + method));
			return property.getName();
		} catch (Exception e) {
			if (e instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) e;
			}
			throw new IllegalArgumentException("Invalid bean getter method: " + method, e);
		}
	}

	@SafeVarargs
	public static <T, S> String[] findPropertyNames(Class<T> beanClass, SerializableFunction1<T, S>... beanGetters) {
		return Stream.of(beanGetters)
				.map(beanGetter -> PropertyNameFinder.findPropertyName(beanClass, beanGetter))
				.toArray(String[]::new);
	}
}
