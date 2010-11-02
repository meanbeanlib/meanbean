package org.meanbean.util;

import java.io.Serializable;

import org.apache.commons.logging.Log;

/**
 * Simple concrete ValidationHelper.
 * 
 * @author Graham Williamson
 */
public class SimpleValidationHelper implements ValidationHelper, Serializable {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /** Logging mechanism. */
    private final transient Log log;
    
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
    public SimpleValidationHelper(Log log) {
        this.log = log;
    }
    
    /**
     * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br/>
     * 
     * The exception thrown will contain the name of the parameter that must exist.
     * 
     * @param name
     *            The name of the object that must exist.
     * @param value
     *            The object that must exist.
     * 
     * @throws IllegalArgumentException
     *             If the specified value does not exist.
     */
    public void ensureExists(String name, Object value) throws IllegalArgumentException {
        log("ensureExists: entering with name=["+name+"], value=["+value+"].");
        if (value == null) {
            log("ensureExists: Object ["+name+"] does not exist. Throw IllegalArgumentException..");
            throw new IllegalArgumentException("Object [" + name + "] must be provided.");
        }
        log("ensureExists: exiting.");
    }

    /**
     * Ensure that the specified value exists, conditionally throwing an IllegalArgumentException if it does not. <br/>
     * 
     * The exception thrown will contain the name of the parameter that must exist as well as the operation being
     * attempted.
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
    public void ensureExists(String name, String operation, Object value) throws IllegalArgumentException {
        log("ensureExists: entering with name=["+name+"], operation=["+operation+"], value=["+value+"].");
        if (value == null) {
            log("ensureExists: Object ["+name+"] does not exist. Throw IllegalArgumentException..");
            throw new IllegalArgumentException("Cannot " + operation + " with null " + name + ".");
        }
        log("ensureExists: exiting.");
    }
    
    /**
     * Log the specified message if logging is configured in this Validation Helper.
     * 
     * @param message
     *            The message to log.
     */
    private void log(String message) {
        if (log != null) {
            log.debug(message);
        }
    }

}
