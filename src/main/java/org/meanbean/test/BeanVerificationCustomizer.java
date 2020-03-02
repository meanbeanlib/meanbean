package org.meanbean.test;

import com.github.meanbeanlib.mirror.SerializableLambdas.SerializableFunction1;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public interface BeanVerificationCustomizer {

    RandomValueGenerator getRandomValueGenerator();

    BeanVerificationCustomizer setRandomValueGenerator(RandomValueGenerator randomValueGenerator);

    FactoryCollection getFactoryCollection();

    BeanVerificationCustomizer setFactoryCollection(FactoryCollection factoryCollection);

    FactoryLookupStrategy getFactoryLookupStrategy();

    BeanVerificationCustomizer setFactoryLookupStrategy(FactoryLookupStrategy factoryLookupStrategy);

    BeanInformationFactory getBeanInformationFactory();

    BeanVerificationCustomizer setBeanInformationFactory(BeanInformationFactory beanInformationFactory);

    /**
     * Register a custom factory for given class
     */
    <T> BeanVerificationCustomizer registerFactory(Class<T> clazz, Factory<? extends T> factory);

    /**
     * Register factory for an inheritance type hierarchy
     */
    <T> BeanVerificationCustomizer registerTypeHierarchyFactory(Class<T> baseType, Factory<T> factory);

    int getDefaultIterations();

    /**
     * Set the number of times a type should be tested by default
     */
    BeanVerificationCustomizer setDefaultIterations(int iterations);

    /**
     * Mark the specified property as one to be disregarded/ignored during testing.
     */
    BeanVerificationCustomizer addIgnoredPropertyName(String property) throws IllegalArgumentException;

    /**
     * Mark the specified property as one to be disregarded/ignored during testing.
     * <pre>
     *     addIgnoredProperty(MyBean::getPropertyValue);
     * </pre>
     */
    <T, S> BeanVerificationCustomizer addIgnoredProperty(SerializableFunction1<T, S> beanGetter) throws IllegalArgumentException;

    /**
     * Register the specified Factory as an override Factory for the specified property. This means that the specified
     * Factory will be used over the standard Factory for the property.
     */
    <T> BeanVerificationCustomizer addOverrideFactory(String property, Factory<T> factory) throws IllegalArgumentException;

    /**
     * Register the specified Factory as an override Factory for the specified property. This means that the specified
     * Factory will be used over the standard Factory for the property.
     * <pre>
     *     addOverridePropertyFactory(MyBean::getPropertyValue, () -&gt; createPropertyValue());
     * </pre>		
     */
    <T, S> BeanVerificationCustomizer addOverridePropertyFactory(SerializableFunction1<T, S> beanGetter, Factory<S> factory);

    /**
     * Add a property that is insignificant for EqualsMethodTester
     */
    <T, S> BeanVerificationCustomizer addEqualsInsignificantProperty(String propertyName);

    /**
     * Add a property that is insignificant for EqualsMethodTester
     * 
     * <pre>
     *     addEqualsInsignificantProperty(MyBean::getPropertyValue);
     * </pre>		
     */
    <T, S> BeanVerificationCustomizer addEqualsInsignificantProperty(SerializableFunction1<T, S> beanGetter);

}