package org.meanbean.util;

import org.meanbean.logging.$Logger;

/**
 * Simple concrete ValidationHelper.
 * 
 * @author Graham Williamson
 */
public class SimpleValidationHelper implements ValidationHelper {

    /** Logging mechanism. */
    private final transient $Logger logger;

    /**
     * Construct a new Simple Validation Helper.
     */
    public SimpleValidationHelper() {
        this(null);
    }

    /**
     * Construct a new Simple Validation Helper.
     * 
     * @param log
     *            Logging mechanism to use when logging validation exceptions.
     */
    public SimpleValidationHelper($Logger logger) {
        this.logger = logger;
    }

    /**
     * <p>
     * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br/>
     * </p>
     * 
     * <p>
     * The exception thrown will contain the name of the parameter that must exist.
     * </p>
     * 
     * @param name
     *            The name of the object that must exist.
     * @param value
     *            The object that must exist.
     * 
     * @throws IllegalArgumentException
     *             If the specified value does not exist.
     */
    @Override
    public void ensureExists(String name, Object value) throws IllegalArgumentException {
        log("ensureExists: entering with name=[{}], value=[{}].", name, value);
        if (value == null) {
            log("ensureExists: Object [{}] does not exist. Throw IllegalArgumentException.", name);
            throw new IllegalArgumentException("Object [" + name + "] must be provided.");
        }
        log("ensureExists: exiting.");
    }

    /**
     * <p>
     * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br/>
     * </p>
     * 
     * <p>
     * The exception thrown will contain the name of the parameter that must exist as well as the operation being
     * attempted.
     * </p>
     * 
     * @param name
     *            The name of the object that must exist.
     * @param operation
     *            The operation being attempted.
     * @param value
     *            The object that must exist.
     * 
     * @throws IllegalArgumentException
     *             If the specified value does not exist.
     */
    @Override
    public void ensureExists(String name, String operation, Object value) throws IllegalArgumentException {
        log("ensureExists: entering with name=[{}], operation=[{}], value=[{}].", name, operation, value);
        if (value == null) {
            log("ensureExists: Object [{}] does not exist. Throw IllegalArgumentException..", name);
            throw new IllegalArgumentException("Cannot " + operation + " with null " + name + ".");
        }
        log("ensureExists: exiting.");
    }

    /**
     * Log the specified message if logging is configured in this Validation Helper.
     */
    private void log(String message, Object... args) {
        if (logger != null) {
            logger.debug(message, args);
        }
    }
}