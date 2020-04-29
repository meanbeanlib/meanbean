package org.meanbean.test.internal;

import org.kohsuke.MetaInfServices;
import org.meanbean.bean.info.PropertyInformation;
import org.meanbean.logging.$Logger;
import org.meanbean.logging.$LoggerFactory;
import org.meanbean.test.BeanTestException;
import org.meanbean.util.AssertionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@MetaInfServices
public class SideEffectDetectorImpl implements SideEffectDetector {

    private static final $Logger logger = $LoggerFactory.getLogger(SideEffectDetectorImpl.class);
    
    private Object bean;
    private List<PropertyInformation> readableWritableProperties;

    private Map<PropertyInformation, EqualityTest> equalityTestsByProperty = new HashMap<>();
    private PropertyInformation propertyUnderTest;
    private Map<String, Object> beforeValues;

    @Override
    public List<PropertyInformation> init(Object bean, List<PropertyInformation> readableWritableProperties) {
        this.bean = bean;
        this.readableWritableProperties = readableWritableProperties;

        Collections.shuffle(readableWritableProperties); // shuffle to detect ordering side-effects
        return readableWritableProperties;
    }

    @Override
    public void beforeTestProperty(PropertyInformation property, EqualityTest equalityTest) {
        propertyUnderTest = property;
        equalityTestsByProperty.put(property, equalityTest);
        beforeValues = saveValues();
    }

    /**
     * see VerifierSettings#suppressWarning(Warning)
     */
    @Override
    public void detectAfterTestProperty() {
        Map<String, Object> afterValues = saveValues();

        // ignore value set by the property just tested.
        beforeValues.remove(propertyUnderTest.getName());
        afterValues.remove(propertyUnderTest.getName());

        for (Map.Entry<String, Object> beforeEntry : beforeValues.entrySet()) {
            String otherProperty = beforeEntry.getKey();

            Object beforeValue = beforeEntry.getValue();
            Object afterValue = afterValues.get(otherProperty);

            EqualityTest equalityTest = equalityTestsByProperty.getOrDefault(otherProperty, EqualityTest.LOGICAL);
            if (beforeValue != null && !equalityTest.test(beforeValue, afterValue)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Side-effect detected.\nBefore={}\nAfter={}", beforeValues, afterValues);
                }
                String message = String
                        .format("Property [%s] appears to have a side-effect on another property [%s]",
                        propertyUnderTest.getName(), otherProperty);
                AssertionUtils.fail(message);
            }
        }
    }

    private Map<String, Object> saveValues() {

        Map<String, Object> values = new TreeMap<>();
        for (PropertyInformation property : readableWritableProperties) {
            try {
                Object value = property.getReadMethod().invoke(bean);
                values.put(property.getName(), value);
            } catch (Exception e) {
                String propertyName = property.getName();
                String message = "Failed to test property [" + propertyName + "] due to Exception [" + e.getClass().getName()
                        + "]: [" + e.getMessage() + "].";
                throw new BeanTestException(message, e);
            }
        }
        return values;
    }
}