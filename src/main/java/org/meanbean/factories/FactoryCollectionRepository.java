package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Mutable FactoryCollection that delegates to other FactoryCollection implementations
 */
@MetaInfServices
@Order(1000)
public class FactoryCollectionRepository implements FactoryCollection {

	@Override
	public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		factoryCollections().forEach(factoryCollection -> factoryCollection.addFactory(clazz, factory));
	}

	@Override
	public Factory<?> getFactory(Class<?> clazz) throws IllegalArgumentException, NoSuchFactoryException {
		Optional<Factory<?>> resultOptional = factoryCollections()
				.filter(factoryCollection -> factoryCollection.hasFactory(clazz))
				.findFirst()
				.map(factoryCollection -> factoryCollection.getFactory(clazz));

		if (resultOptional.isPresent()) {
			return resultOptional.get();
		}

		return factoryCollections().findFirst()
				.get()
				.getFactory(clazz);
	}

	@Override
	public boolean hasFactory(Class<?> clazz) throws IllegalArgumentException {
		return factoryCollections().anyMatch(factoryCollection -> factoryCollection.hasFactory(clazz));
	}

	private synchronized Stream<FactoryCollection> factoryCollections() {
		return FactoryCollection.getServiceDefinition()
				.getServiceFactory()
				.getAll()
				.stream()
				.filter(factoryCollection -> factoryCollection != this);
	}
}