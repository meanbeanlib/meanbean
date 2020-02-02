package org.meanbean.factories;

import org.meanbean.lang.Factory;
import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Concrete Factory that creates instances of the type of object specified during construction of the Factory. Only
 * classes that have a no-argument constructor can be successfully instantiated by this Factory. If the class does not
 * have a no-argument constructor, an exception will be thrown when <code>create()</code> is invoked.
 * 
 * @author Graham Williamson
 */
public class BasicNewObjectInstanceFactory implements Factory<Object> {

	/** Logging mechanism. */
	private static final $Logger logger = $LoggerFactory.getLogger(BasicNewObjectInstanceFactory.class);

	/** Input validation helper. */
	private final ValidationHelper validationHelper = new SimpleValidationHelper(logger);

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
		Object result = null;
		try {
			Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
			declaredConstructor.setAccessible(true);
			result = declaredConstructor.newInstance();
		} catch (InstantiationException e) {
			wrapAndRethrowException(e);
		} catch (IllegalAccessException e) {
			wrapAndRethrowException(e);
		} catch (SecurityException e) {
			wrapAndRethrowException(e);
		} catch (NoSuchMethodException e) {
			wrapAndRethrowException(e);
		} catch (IllegalArgumentException e) {
			wrapAndRethrowException(e);
		} catch (InvocationTargetException e) {
			wrapAndRethrowException(e);
		}
		return result;
	}

	/**
	 * Wraps the specified Exception within an ObjectCreationException and throws it.
	 * 
	 * @param exception
	 *            The Exception to wrap and (re)throw.
	 * 
	 * @throws ObjectCreationException
	 *             The ObjectCreationException wrapping the specified Exception.
	 */
	private void wrapAndRethrowException(Exception exception) throws ObjectCreationException {
		String message =
		        "Failed to instantiate object of type [" + clazz.getName() + "] due to "
		                + exception.getClass().getSimpleName() + ".";
		throw new ObjectCreationException(message, exception);
	}
}