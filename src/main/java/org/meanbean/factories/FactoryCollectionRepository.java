package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;

import java.lang.reflect.Type;
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
	public Factory<?> getFactory(Type type) throws IllegalArgumentException, NoSuchFactoryException {
		Optional<Factory<?>> resultOptional = factoryCollections()
				.filter(factoryCollection -> factoryCollection.hasFactory(type))
				.findFirst()
				.map(factoryCollection -> factoryCollection.getFactory(type));

		if (resultOptional.isPresent()) {
			return resultOptional.get();
		}

		throw new NoSuchFactoryException("No factory found for " + type);
	}

	@Override
	public boolean hasFactory(Type type) throws IllegalArgumentException {
		return factoryCollections().anyMatch(factoryCollection -> factoryCollection.hasFactory(type));
	}

	protected Stream<FactoryCollection> factoryCollections() {
		return FactoryCollection.getServiceDefinition()
				.getServiceFactory()
				.getAll()
				.stream()
				.filter(factoryCollection -> factoryCollection != this);
	}
}