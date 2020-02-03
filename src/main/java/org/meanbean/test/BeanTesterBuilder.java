package org.meanbean.test;

import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryLookup;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

import java.lang.reflect.Type;

public class BeanTesterBuilder {

	private int iterations = BeanTester.TEST_ITERATIONS_PER_BEAN;

	private RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();

	private FactoryCollection factoryCollection = FactoryCollection.getInstance();

	private FactoryLookupStrategy factoryLookupStrategy = FactoryLookupStrategy.getInstance();

	private BeanInformationFactory beanInformationFactory = BeanInformationFactory.getInstance();

	private BeanPropertyTester beanPropertyTester = new BeanPropertyTester();

	public int getIterations() {
		return iterations;
	}

	public BeanTesterBuilder setIterations(int iterations) {
		this.iterations = iterations;
		return this;
	}

	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	public BeanTesterBuilder setRandomValueGenerator(RandomValueGenerator randomValueGenerator) {
		this.randomValueGenerator = randomValueGenerator;
		return this;
	}

	public FactoryCollection getFactoryCollection() {
		return factoryCollection;
	}

	public BeanTesterBuilder setFactoryCollection(FactoryCollection factoryCollection) {
		this.factoryCollection = factoryCollection;
		return this;
	}

	public FactoryLookupStrategy getFactoryLookupStrategy() {
		return factoryLookupStrategy;
	}

	public BeanTesterBuilder setFactoryLookupStrategy(FactoryLookupStrategy factoryLookupStrategy) {
		this.factoryLookupStrategy = factoryLookupStrategy;
		return this;
	}

	public BeanInformationFactory getBeanInformationFactory() {
		return beanInformationFactory;
	}

	public BeanTesterBuilder setBeanInformationFactory(BeanInformationFactory beanInformationFactory) {
		this.beanInformationFactory = beanInformationFactory;
		return this;
	}

	public BeanPropertyTester getBeanPropertyTester() {
		return beanPropertyTester;
	}

	public BeanTesterBuilder setBeanPropertyTester(BeanPropertyTester beanPropertyTester) {
		this.beanPropertyTester = beanPropertyTester;
		return this;
	}

	public <T> BeanTesterBuilder registerFactory(Class<T> clazz, Factory<? extends T> factory) {
		getFactoryCollection().addFactory(clazz, factory);
		return this;
	}

	/**
	 * Register factory for an inheritance type hierarchy
	 */
	public <T> BeanTesterBuilder registerTypeHierarchyFactory(Class<T> baseType, Factory<T> factory) {
		getFactoryCollection().addFactoryLookup(new FactoryLookup() {

			@Override
			public boolean hasFactory(Type clazz) throws IllegalArgumentException {
				return clazz instanceof Class && baseType.isAssignableFrom((Class<?>) clazz);
			}

			@SuppressWarnings("unchecked")
			@Override
			public <E> Factory<E> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
				if (hasFactory(type)) {
					return (Factory<E>) factory;
				}
				throw new NoSuchFactoryException("No factory for " + type);
			}
		});
		return this;
	}

	public BeanTester build() {
		return new BeanTester(iterations,
				randomValueGenerator,
				factoryCollection,
				factoryLookupStrategy,
				beanInformationFactory,
				beanPropertyTester);
	}
}