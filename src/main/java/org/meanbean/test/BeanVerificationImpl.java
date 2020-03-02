/*-
 * ​​​
 * meanbean
 * ⁣⁣⁣
 * Copyright (C) 2010 - 2020 the original author or authors.
 * ⁣⁣⁣
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ﻿﻿﻿﻿﻿
 */

package org.meanbean.test;

import com.github.meanbeanlib.mirror.SerializableLambdas.SerializableFunction1;
import org.meanbean.bean.info.BeanInformationFactory;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.util.FactoryLookupStrategy;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.ServiceFactory;

import java.util.function.Consumer;

/**
 * For one-shot or fluent assertions
 */
class BeanVerificationImpl implements BeanVerification, BeanVerificationCustomizer {

    private Class<?> beanClass;
    private BeanTesterBuilder builder = BeanTesterBuilder.newBeanTesterBuilder();

    public BeanVerificationImpl(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanVerification with(Consumer<BeanVerificationCustomizer> beanVerificationCustomizer) {
        beanVerificationCustomizer.accept(this);
        return this;
    }

    @Override
    public BeanVerification isValidJavaBean() {
        ServiceFactory.inScope(() -> {
            builder.build().testBean(beanClass);
        });
        return this;
    }

    @Override
    public BeanVerification hasValidEqualsMethod() {
        ServiceFactory.inScope(() -> {
            builder.buildEqualsMethodTester().testEqualsMethod(beanClass);
        });
        return this;
    }

    @Override
    public BeanVerification hasValidHashCodeMethod() {
        ServiceFactory.inScope(() -> {
            builder.buildHashCodeMethodTester().testHashCodeMethod(beanClass);
        });
        return this;
    }

    @Override
    public BeanVerification hasValidToStringMethod() {
        ServiceFactory.inScope(() -> {
            builder.buildToStringMethodTester().testToStringMethod(beanClass);
        });
        return this;
    }

    @Override
    public RandomValueGenerator getRandomValueGenerator() {
        return builder.getRandomValueGenerator();
    }

    @Override
    public BeanVerificationCustomizer setRandomValueGenerator(RandomValueGenerator randomValueGenerator) {
        builder.setRandomValueGenerator(randomValueGenerator);
        return this;
    }

    @Override
    public FactoryCollection getFactoryCollection() {
        return builder.getFactoryCollection();
    }

    @Override
    public BeanVerificationCustomizer setFactoryCollection(FactoryCollection factoryCollection) {
        builder.setFactoryCollection(factoryCollection);
        return this;
    }

    @Override
    public FactoryLookupStrategy getFactoryLookupStrategy() {
        return builder.getFactoryLookupStrategy();
    }

    @Override
    public BeanVerificationCustomizer setFactoryLookupStrategy(FactoryLookupStrategy factoryLookupStrategy) {
        builder.setFactoryLookupStrategy(factoryLookupStrategy);
        return this;
    }

    @Override
    public BeanInformationFactory getBeanInformationFactory() {
        return builder.getBeanInformationFactory();
    }

    @Override
    public BeanVerificationCustomizer setBeanInformationFactory(BeanInformationFactory beanInformationFactory) {
        builder.setBeanInformationFactory(beanInformationFactory);
        return this;
    }

    @Override
    public <T> BeanVerificationCustomizer registerFactory(Class<T> clazz, Factory<? extends T> factory) {
        builder.registerFactory(clazz, factory);
        return this;
    }

    @Override
    public <T> BeanVerificationCustomizer registerTypeHierarchyFactory(Class<T> baseType, Factory<T> factory) {
        builder.registerTypeHierarchyFactory(baseType, factory);
        return this;
    }

    @Override
    public int getDefaultIterations() {
        return builder.getDefaultIterations();
    }

    @Override
    public BeanVerificationCustomizer setDefaultIterations(int iterations) {
        builder.setDefaultIterations(iterations);
        return this;
    }

    @Override
    public BeanVerificationCustomizer addIgnoredPropertyName(String property) {
        builder.addIgnoredPropertyName(beanClass(), property);
        return this;
    }

    @Override
    public <T, S> BeanVerificationCustomizer addIgnoredProperty(SerializableFunction1<T, S> beanGetter) {
        builder.addIgnoredProperty(beanClass(), beanGetter);
        return this;
    }

    @Override
    public <T> BeanVerificationCustomizer addOverrideFactory(String property, Factory<T> factory) {
        builder.addOverrideFactory(beanClass(), property, factory);
        return this;
    }

    @Override
    public <T, S> BeanVerificationCustomizer addOverridePropertyFactory(SerializableFunction1<T, S> beanGetter,
            Factory<S> factory) {
        builder.addOverridePropertyFactory(beanClass(), beanGetter, factory);
        return this;
    }

    @Override
    public <T, S> BeanVerificationCustomizer addEqualsInsignificantProperty(String propertyName) {
        builder.addEqualsInsignificantProperty(beanClass(), propertyName);
        return this;
    }

    @Override
    public <T, S> BeanVerificationCustomizer addEqualsInsignificantProperty(SerializableFunction1<T, S> beanGetter) {
        builder.addEqualsInsignificantProperty(beanClass(), beanGetter);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> Class<T> beanClass() {
        return (Class) beanClass;
    }
}