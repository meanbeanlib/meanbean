package org.meanbean.test.internal;

import org.meanbean.bean.info.PropertyInformation;

import java.util.List;

public class NoopSideEffectDetector implements SideEffectDetector {

    public static final NoopSideEffectDetector INSTANCE = new NoopSideEffectDetector();

    @Override
    public List<PropertyInformation> init(Object bean, List<PropertyInformation> readableWritableProperties) {
        return readableWritableProperties;
    }

    @Override
    public void beforeTestProperty(PropertyInformation property, EqualityTest equalityTest) {

    }

    @Override
    public void detectAfterTestProperty() {

    }

}