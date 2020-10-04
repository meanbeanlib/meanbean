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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.meanbean.test.beans.NonBean;
import org.meanbean.test.beans.domain.Address;
import org.meanbean.test.beans.domain.Item;

public class ToStringMethodTesterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSimple() {
        ToStringMethodTester tester = new ToStringMethodTester(Configuration.defaultConfigurationProvider());
        tester.testToStringMethod(Address.class);
    }

    @Test
    public void testNoOverride() {
        ToStringMethodTester tester = new ToStringMethodTester(Configuration.defaultConfigurationProvider());

        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected org.meanbean.test.beans.domain.Item class to override toString()");
        tester.testToStringMethod(Item.class);
    }

    @Test
    public void testJavaBean() {
        BeanVerifier.forClass(BeanWithBadProperties.class)
                .withSettings(settings -> settings.addIgnoredProperty(BeanWithBadProperties::getId))
                .withSettings(settings -> settings.addIgnoredProperty(BeanWithBadProperties::getBrokenProperty))
                .withSettings(settings -> settings.addOverridePropertyFactory(BeanWithBadProperties::getNonBean, () -> new NonBean("foo")))
                .verify();
    }

    public static class BeanWithBadProperties extends Address {
        private NonBean nonBean;

        public NonBean getNonBean() {
            return nonBean;
        }

        public void setNonBean(NonBean nonBean) {
            this.nonBean = nonBean;
        }

        public String getBrokenProperty() {
            throw new RuntimeException();
        }

        public void setBrokenProperty(String str) {
            throw new RuntimeException();
        }
    }
}
