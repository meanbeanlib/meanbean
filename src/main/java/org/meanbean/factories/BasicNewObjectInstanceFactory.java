package org.meanbean.factories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete Factory that creates instances of the type of object specified during construction of the Factory. Only
 * classes that have a no-argument constructor can be successfully instantiated by this Factory. If the class does not
 * have a no-argument constructor, an exception will be thrown when <code>create()</code> is invoked.
 * 
 * @author Graham Williamson
 */
public class BasicNewObjectInstanceFactory implements Factory<Object> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	/** Logging mechanism. */
	private final Log log = LogFactory.getLog(BasicNewObjectInstanceFactory.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(log);

	/** The type of Object this Factory should create new instances of. */
	private final Class<?> clazz;

	/**
	 * Construct a basic new object instance Factory.
	 * 
	 * @param clazz
	 *            The type of Object the Factory should create new instances of.
	 * 
	 * @throws IllegalArgumentException
	 *             If the specified clazz is deemed illegal. For example, if it is null.
	 */
	public BasicNewObjectInstanceFactory(Class<?> clazz) throws IllegalArgumentException {
		validationHelper.ensureExists("clazz", "construct Factory", clazz);
		this.clazz = clazz;
	}

	/**
	 * Create a new instance of the type specified during construction of this Factory. The type must have a no-arg
	 * constructor for this to work.
	 * 
	 * @return A new instance of the type specified during construction of this Factory.
	 * 
	 * @throws ObjectCreationException
	 *             If an instance of the type cannot be constructed, perhaps due to it not having a no-arg constructor.
	 */
	@Override
	public Object create() throws ObjectCreationException {
		log.debug("create: entering.");
		Object result;
		try {
			result = clazz.newInstance();
		} catch (InstantiationException e) {
			String message =
			        "Failed to instantiate object of type [" + clazz.getName() + "] due to InstantiationException.";
			log.debug("create: " + message + " Throw ObjectCreationException.", e);
			throw new ObjectCreationException(message, e);
		} catch (IllegalAccessException e) {
			String message =
			        "Failed to instantiate object of type [" + clazz.getName() + "] due to IllegalAccessException.";
			log.debug("create: " + message + " Throw ObjectCreationException.", e);
			throw new ObjectCreationException(message, e);
		}
		log.debug("create: exiting returning [" + result + "].");
		return result;
	}
}