package org.meanbean.test;

/**
 * Enum of warnings that can be suppressed in {@link BeanVerifier}.
 */
public enum Warning {

    /**
     * Disables the check for setter methods that have side-effect other properties. For example,
     * a setter method that sets prroperty fields.
     */
    SETTER_SIDE_EFFECT,

}
