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

import org.meanbean.util.ClassPath;
import org.meanbean.util.ClassPath.ClassInfo;
import org.meanbean.util.ServiceFactory;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.function.Consumer;

/**
 * For one-shot or fluent assertions
 */
public interface BeanVerification {

    /**
     * Start a fluent bean verification chain for the given beanClass 
     */
    public static BeanVerification verifyThat(Class<?> beanClass) {
        return new BeanVerificationImpl(beanClass);
    }

    /**
     * Verify that given beanClass has valid bean getters/setters, equals/hashCode, and toString methods
     */
    public static void verifyBean(Class<?> beanClass) {
        verifyThat(beanClass).passesAllValidations();
    }

    public static void verifyBeans(Class<?>... beanClasses) {
        for (Class<?> beanClass : beanClasses) {
            try {
                BeanVerification.verifyBean(beanClass);
            } catch (AssertionError | RuntimeException e) {
                throw new AssertionError("Cannot verify bean type " + beanClass.getName(), e);
            }
        }
    }

    public static void verifyBeansIn(String packageName) {
        ClassPath classPath = ClassPath.from(BeanVerification.class);
        Set<ClassInfo> classInfoSet = classPath.getTopLevelClassesRecursive(packageName);
        Class<?>[] beanClasses = classInfoSet.stream()
                .map(ClassInfo::load)
                .filter(clazz -> !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()))
                .toArray(Class<?>[]::new);
        verifyBeans(beanClasses);
    }

    public static void verifyBeansIn(Package packageMarker) {
        verifyBeansIn(packageMarker.getName());
    }

    /**
     * Customizes bean verification behavior. Example:
     * <pre>
     *   verifyThat(MyBean.class)
     *       .with(customizer -&gt; customizer.registerFactory(MyOtherBean.class, () -&gt; createMyOtherBean())
     * </pre>
     */
    public BeanVerification with(Consumer<BeanVerificationCustomizer> beanVerificationCustomizer);

    public BeanVerification hasValidEqualsMethod();

    public BeanVerification hasValidHashCodeMethod();

    public BeanVerification isValidJavaBean();

    public BeanVerification hasValidToStringMethod();

    public default void passesAllValidations() {
        ServiceFactory.inScope(() -> {
            isValidJavaBean().hasValidEqualsMethod()
                    .hasValidHashCodeMethod()
                    .hasValidToStringMethod();
        });
    }

}