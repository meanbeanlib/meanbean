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

package org.meanbean.factories;

import org.kohsuke.MetaInfServices;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@MetaInfServices
public class ConcurrentFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		factoryCollection.addFactory(AtomicInteger.class, newFactory(randomValueGenerator::nextInt, AtomicInteger::new));
		factoryCollection.addFactory(AtomicLong.class, newFactory(randomValueGenerator::nextLong, AtomicLong::new));
		factoryCollection.addFactory(AtomicBoolean.class, () -> new AtomicBoolean(randomValueGenerator.nextBoolean()));
    }

    private <A extends Number, N extends Number> Factory<A> newFactory(Factory<N> factory, Function<N, A> fn) {
        return () -> fn.apply(factory.create());
    }
}
