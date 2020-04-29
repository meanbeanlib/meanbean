package org.meanbean.test.internal;

import org.meanbean.bean.info.PropertyInformation;

import java.util.List;

public interface SideEffectDetectorFactory {

    List<PropertyInformation> init(Object bean, List<PropertyInformation> readableWritableProperties);

    void beforeTestProperty(PropertyInformation property, EqualityTest equalityTest);

    void detectAfterTestProperty();

}