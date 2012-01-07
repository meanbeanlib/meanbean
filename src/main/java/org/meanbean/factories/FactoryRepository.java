package org.meanbean.factories;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.factories.util.FactoryIdGenerator;
import org.meanbean.factories.util.SimpleFactoryIdGenerator;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete collection factories of different types of objects.
 * 
 * @author Graham Williamson
 */
public final class FactoryRepository implements FactoryCollection, RandomValueGeneratorProvider {

	/** Lock to control concurrent access to the internal state of the repository; namely the factories Map. */
	private final ReadWriteLock factoriesLock = new ReentrantReadWriteLock();

	/** A Map of Factory objects keyed by a unique ID. */
	private final Map<String, Factory<?>> factories = new HashMap<String, Factory<?>>();

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator;

	/** Helper that generates keys that are used to key Factories in the factories Map. */
	private final FactoryIdGenerator keyGenerator = new SimpleFactoryIdGenerator();

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(FactoryRepository.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/**
	 * Construct a new Factory Repository.
	 * 
	 * @param randomValueGenerator
	 *            The random value generator used by factories to randomly generate values.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified randomValueGenerator is deemed illegal. For example, if it is <code>null</code>.
	 */
	public FactoryRepository(RandomValueGenerator randomValueGenerator) throws IllegalArgumentException {
		log.debug("FactoryRepository: entering");
		validationHelper.ensureExists("randomValueGenerator", "construct FactoryRepository", randomValueGenerator);
		this.randomValueGenerator = randomValueGenerator;
		initialize();
		log.debug("FactoryRepository: exiting");
	}

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	public RandomValueGenerator getRandomValueGenerator() {
		log.debug("getRandomNumberGenerator: entering");
		log.debug("getRandomNumberGenerator: exiting returning [" + randomValueGenerator + "].");
		return randomValueGenerator;
	}

	/**
	 * Initialize the repository prior to public use.
	 */
	private void initialize() {
		log.debug("initialize: entering");
		new PrimitiveFactoryPlugin().initialize(this, this);
		new ObjectFactoryPlugin().initialize(this, this);
		new CollectionFactoryPlugin().initialize(this, this);
		log.debug("initialize: exiting");
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
	public void addFactory(Class<?> clazz, Factory<?> factory) throws IllegalArgumentException {
		log.debug("addFactory: entering with clazz=[" + clazz + "], factory=[" + factory + "].");
		validationHelper.ensureExists("clazz", "add Factory", clazz);
		validationHelper.ensureExists("factory", "add Factory", factory);
		String key = keyGenerator.createIdFromClass(clazz);// Should have prevented Exceptions in Validation above
		try {
			factoriesLock.writeLock().lock();
			factories.put(key, factory);
		} finally {
			factoriesLock.writeLock().unlock();
		}
		log.debug("addFactory: exiting.");
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
	public Factory<?> getFactory(Class<?> clazz) throws IllegalArgumentException, NoSuchFactoryException {
		log.debug("getFactory: entering with clazz=[" + clazz + "].");
		validationHelper.ensureExists("clazz", "get Factory", clazz);
		String key = keyGenerator.createIdFromClass(clazz);// Should have prevented Exceptions in Validation above
		Factory<?> factory;
		try {
			factoriesLock.readLock().lock();
			factory = factories.get(key);
		} finally {
			factoriesLock.readLock().unlock();
		}
		if (factory == null) {
			String message = "Failed to find a Factory registered against [" + key + "] in the Repository.";
			log.debug("getFactory: " + message + " Throw NoSuchFactoryException.");
			throw new NoSuchFactoryException(message);
		}
		log.debug("getFactory: exiting returning [" + factory + "].");
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
	public boolean hasFactory(Class<?> clazz) throws IllegalArgumentException {
		log.debug("hasFactory: entering with clazz=[" + clazz + "].");
		validationHelper.ensureExists("clazz", "check collection for Factory", clazz);
		String key = keyGenerator.createIdFromClass(clazz);// Should have prevented Exceptions in Validation above
		try {
			factoriesLock.readLock().lock();
			boolean result = factories.containsKey(key);
			log.debug("hasFactory: exiting returning [" + result + "].");
			return result;
		} finally {
			factoriesLock.readLock().unlock();
		}
	}
}