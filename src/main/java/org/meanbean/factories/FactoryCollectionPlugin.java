package org.meanbean.factories;

import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceDefinition;

import java.util.List;

/**
 * Defines a plugin that will register Factories with the specified FactoryCollection.
 * 
 * @author Graham Williamson
 */
public interface FactoryCollectionPlugin {

	public static ServiceDefinition<FactoryCollectionPlugin> getServiceDefinition() {
		return new ServiceDefinition<>(FactoryCollectionPlugin.class);
	}

	public static List<FactoryCollectionPlugin> getInstances() {
		return getServiceDefinition().getServiceFactory()
				.getAll();
	}
	
	/**
	 * Initialize the plugin, adding Factories to the FactoryCollection.
	 * 
	 * @param factoryCollection
	 *            A FactoryCollection that Factory objects can be added to.
	 * @param randomValueGenerator RandomValueGenerator that can be used by Factory objects.
	 */
	void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator);
}