package org.meanbean.bean.info;


/**
 * Factory that creates PropertyInformation objects. Should be used for testing only.
 * 
 * @author Graham Williamson
 */
public class PropertyInformationFactory {

    /**
     * Create a new read-only PropertyInformation object with the specified name. The new PropertyInformation will not
     * have a read method nor write method.
     * 
     * @param name
     *            The name of the property.
     * 
     * @return A new PropertyInformation object.
     */
    public static PropertyInformation createReadOnlyProperty(String name) {
        return create(name, true, false);
    }

    /**
     * Create a new write-only PropertyInformation object with the specified name. The new PropertyInformation will not
     * have a read method nor write method.
     * 
     * @param name
     *            The name of the property.
     * 
     * @return A new PropertyInformation object.
     */
    public static PropertyInformation createWriteOnlyProperty(String name) {
        return create(name, false, true);
    }

    /**
     * Create a new readable/writable PropertyInformation object with the specified name. The new PropertyInformation
     * will not have a read method nor write method.
     * 
     * @param name
     *            The name of the property.
     * 
     * @return A new PropertyInformation object.
     */
    public static PropertyInformation createReadWriteProperty(String name) {
        return create(name, true, true);
    }

    private static PropertyInformation create(String name, boolean isReadable, boolean isWritable) {
        PropertyInformationBean propertyInformation = new PropertyInformationBean();
        propertyInformation.setName(name);
        propertyInformation.setReadable(isReadable);
        propertyInformation.setWritable(isWritable);
        propertyInformation.setReadMethod(null);
        propertyInformation.setWriteMethod(null);
        propertyInformation.setReadMethodReturnType(null);
        propertyInformation.setWriteMethodParameterType(null);
        return propertyInformation;
    }
}