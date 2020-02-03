package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.Order;
import org.meanbean.util.TypeToken;

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
		return getFactory(TypeToken.get(clazz));
	}

	@Override
	public <T> Factory<?> getFactory(TypeToken<?> typeToken) throws IllegalArgumentException, NoSuchFactoryException {
		Optional<Factory<?>> resultOptional = factoryCollections()
				.filter(factoryCollection -> factoryCollection.hasFactory(typeToken))
				.findFirst()
				.map(factoryCollection -> factoryCollection.getFactory(typeToken));

		if (resultOptional.isPresent()) {
			return resultOptional.get();
		}

		throw new NoSuchFactoryException("No factory found for " + typeToken);
	}

	@Override
	public boolean hasFactory(Class<?> clazz) throws IllegalArgumentException {
		return factoryCollections().anyMatch(factoryCollection -> factoryCollection.hasFactory(clazz));
	}

	protected Stream<FactoryCollection> factoryCollections() {
		return FactoryCollection.getServiceDefinition()
				.getServiceFactory()
				.getAll()
				.stream()
				.filter(factoryCollection -> factoryCollection != this);
	}
}