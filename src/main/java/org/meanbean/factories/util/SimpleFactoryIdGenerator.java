package org.meanbean.factories.util;

import java.io.Serializable;

import org.meanbean.util.SimpleValidationHelper;
import org.meanbean.util.ValidationHelper;

/**
 * Concrete Factory ID Generator.
 * 
 * @author Graham Williamson
 */
public class SimpleFactoryIdGenerator implements FactoryIdGenerator, Serializable {

    /** Unique version ID of this Serializable class. */
    private static final long serialVersionUID = 1L;
    
    /** Input validation helper. */
    private final ValidationHelper validationHelper = new SimpleValidationHelper();
    
    /**
     * Create an ID for the specified class.
     * 
     * @param clazz
     *            The class for which a unique ID should be generated.
     * 
     * @return A unique ID, generated for the specified class.
     * 
     * @throws IllegalArgumentException
     *             If the clazz parameter is deemed illegal. For example, if it is null.
     */
    public String createIdFromClass(Class<?> clazz) throws IllegalArgumentException {
        validationHelper.ensureExists("clazz", "create a key", clazz);
        return clazz.getName();
    }
}