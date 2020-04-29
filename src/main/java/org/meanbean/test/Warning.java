package org.meanbean.test;

/**
 * Enum of warnings that can be suppressed in {@link BeanVerifier}.
 */
public enum Warning {

    /**
     * Disables the check and do not fail for setter methods that have side-effect other properties. For example,
     * a setter method that sets prroperty fields.
     */
    SETTER_SIDE_EFFECT,

    /**
     * When meanbean finds a property type that does not have built-in support for creating random values (like it does 
     * with String, Date, etc), then meanbean creates a dynamic factory hoping that the property type is a java bean.
     * That dynamic factory is used to create random values of the property. 
     * 
     * When that happens a warning is logged. This can be used to suppress that (or call VerifierSettings::registerFactory
     * to register a custom factory)
     */
    DYNAMICALLY_CREATED_FACTORY,
    
    ;
}
