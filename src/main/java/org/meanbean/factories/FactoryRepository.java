package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.factories.net.NetFactoryPlugin;
import org.meanbean.factories.time.TimePlugin;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ValidationHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Concrete collection factories of different types of objects.
 * 
 * @author Graham Williamson
 */
@MetaInfServices(FactoryCollection.class)
public final class FactoryRepository implements FactoryCollection {

	/** A Map of Factory objects keyed by a unique ID. */
	private final Map<String, Factory<?>> factories = new ConcurrentHashMap<>();

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();

	public FactoryRepository() throws IllegalArgumentException {
		initialize();
	}

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	public RandomValueGenerator getRandomValueGenerator() {
		return randomValueGenerator;
	}

	/**
	 * Initialize the repository prior to public use.
	 */
	private void initialize() {
		new PrimitiveFactoryPlugin().initialize(this, randomValueGenerator);
		new ObjectFactoryPlugin().initialize(this, randomValueGenerator);
		new CollectionFactoryPlugin().initialize(this, randomValueGenerator);
		new ConcurrentFactoryPlugin().initialize(this, randomValueGenerator);
		new NetFactoryPlugin().initialize(this, randomValueGenerator);
		new TimePlugin().initialize(this, randomValueGenerator);
	}

	/**
	 * <p>
	 * Add the specified Factory to the collection.
	 * </p>
	 * 
	 * <p>
	 * If a Factory is already registered against the specified class, the existing registered Factory will be replaced
	 * with the Factory you specify here.
	 * </p>
	 * 
	 * @param clazz
	 *            The type of objects the Factory creates. The class type will be used to generate a key with which the
	 *            Factory can be retrieved from the collection at a later stage.
	 * @param factory
	 *            The Factory to add to the collection.
	 * 
	 * @throws IllegalArgumentException
	 *             If either of the required parameters are deemed illegal.
	 */
	@Override
	public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		ValidationHelper.ensureExists("clazz", "add Factory", clazz);
		ValidationHelper.ensureExists("factory", "add Factory", factory);
		String key = createId(clazz);// Should have prevented Exceptions in Validation above
		factories.put(key, factory);
	}

	/**
	 * <p>
	 * Get the Factory registered for the specified class.
	 * </p>
	 * 
	 * <p>
	 * To check whether a Factory is registered for a specified class, please refer to
	 * <code>hasFactory(Class<?> clazz);</code>.
	 * </p>
	 * 
	 * @param clazz
	 *            The class the Factory is registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return The requested Factory.
	 * 
	 * @throws IllegalArgumentException
	 *             If the class is deemed illegal.
	 * @throws NoSuchFactoryException
	 *             If the collection does not contain a Factory registered against the specified class.
	 */
	@Override
	public Factory<?> getFactory(Class<?> clazz) throws IllegalArgumentException, NoSuchFactoryException {
		ValidationHelper.ensureExists("clazz", "get Factory", clazz);
		String key = createId(clazz);// Should have prevented Exceptions in Validation above
		Factory<?> factory = factories.get(key);
		if (factory == null) {
            String message = "Failed to find a Factory registered against [" + key + "] in the Repository.";
			throw new NoSuchFactoryException(message);
		}
		return factory;
	}

	/**
	 * Does the collection contain a Factory registered against the specified class?
	 * 
	 * @param clazz
	 *            The class a Factory could be registered against. This should be the type of object that the Factory
	 *            creates.
	 * 
	 * @return <code>true</code> if the collection contains a Factory registered for the specified class;
	 *         <code>false</code> otherwise.
	 * 
	 * @throws IllegalArgumentException
	 *             If the clazz is deemed illegal.
	 */
	@Override
	public boolean hasFactory(Class<?> clazz) throws IllegalArgumentException {
		ValidationHelper.ensureExists("clazz", "check collection for Factory", clazz);
		String key = createId(clazz);// Should have prevented Exceptions in Validation above
		boolean result = factories.containsKey(key);
		return result;
	}

	private String createId(Class<?> clazz) {
		return clazz.getName();
	}
}