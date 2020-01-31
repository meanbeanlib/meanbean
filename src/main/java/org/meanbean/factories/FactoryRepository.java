package org.meanbean.factories;

import org.meanbean.factories.net.NetFactoryPlugin;
import org.meanbean.factories.time.TimePlugin;
import org.meanbean.factories.util.FactoryIdGenerator;
import org.meanbean.factories.util.SimpleFactoryIdGenerator;
import org.meanbean.lang.Factory;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueGeneratorProvider;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Concrete collection factories of different types of objects.
 * 
 * @author Graham Williamson
 */
public final class FactoryRepository implements FactoryCollection, RandomValueGeneratorProvider {

    /** Logging mechanism. */
    private static final $Logger logger = $LoggerFactory.getLogger(FactoryRepository.class);

	/** A Map of Factory objects keyed by a unique ID. */
	private final Map<String, Factory<?>> factories = new ConcurrentHashMap<>();

	/** Random number generator used by factories to randomly generate values. */
	private final RandomValueGenerator randomValueGenerator;

	/** Helper that generates keys that are used to key Factories in the factories Map. */
	private final FactoryIdGenerator keyGenerator = new SimpleFactoryIdGenerator();

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(logger);

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
		logger.debug("FactoryRepository: entering");
		validationHelper.ensureExists("randomValueGenerator", "construct FactoryRepository", randomValueGenerator);
		this.randomValueGenerator = randomValueGenerator;
		initialize();
		logger.debug("FactoryRepository: exiting");
	}

	/**
	 * Get a RandomNumberGenerator.
	 * 
	 * @return A RandomNumberGenerator.
	 */
	@Override
	public RandomValueGenerator getRandomValueGenerator() {
		logger.debug("getRandomNumberGenerator: entering");
		logger.debug("getRandomNumberGenerator: exiting returning [{}].", randomValueGenerator);
		return randomValueGenerator;
	}

	/**
	 * Initialize the repository prior to public use.
	 */
	private void initialize() {
		logger.debug("initialize: entering");
		new PrimitiveFactoryPlugin().initialize(this, this);
		new ObjectFactoryPlugin().initialize(this, this);
		new CollectionFactoryPlugin().initialize(this, this);
		new ConcurrentFactoryPlugin().initialize(this, this);
		new NetFactoryPlugin().initialize(this, this);
		new TimePlugin().initialize(this, this);
		logger.debug("initialize: exiting");
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
		logger.debug("addFactory: entering with clazz=[{}], factory=[{}].", clazz, factory);
		validationHelper.ensureExists("clazz", "add Factory", clazz);
		validationHelper.ensureExists("factory", "add Factory", factory);
		String key = keyGenerator.createIdFromClass(clazz);// Should have prevented Exceptions in Validation above
		factories.put(key, factory);
		logger.debug("addFactory: exiting.");
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
		logger.debug("getFactory: entering with clazz=[{}].", clazz);
		validationHelper.ensureExists("clazz", "get Factory", clazz);
		String key = keyGenerator.createIdFromClass(clazz);// Should have prevented Exceptions in Validation above
		Factory<?> factory = factories.get(key);
		if (factory == null) {
            String message = "Failed to find a Factory registered against [" + key + "] in the Repository.";
			logger.debug("getFactory: {} Throw NoSuchFactoryException.", message);
			throw new NoSuchFactoryException(message);
		}
		logger.debug("getFactory: exiting returning [{}].", factory);
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
		logger.debug("hasFactory: entering with clazz=[{}].", clazz);
		validationHelper.ensureExists("clazz", "check collection for Factory", clazz);
		String key = keyGenerator.createIdFromClass(clazz);// Should have prevented Exceptions in Validation above
		boolean result = factories.containsKey(key);
		logger.debug("hasFactory: exiting returning [{}].", result);
		return result;
	}
}