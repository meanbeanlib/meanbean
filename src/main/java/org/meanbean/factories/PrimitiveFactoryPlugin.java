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
import org.meanbean.factories.basic.BooleanFactory;
import org.meanbean.factories.basic.ByteFactory;
import org.meanbean.factories.basic.CharacterFactory;
import org.meanbean.factories.basic.DoubleFactory;
import org.meanbean.factories.basic.FloatFactory;
import org.meanbean.factories.basic.IntegerFactory;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.ShortFactory;
import org.meanbean.util.RandomValueGenerator;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create Java primitives.
 * 
 * @author Graham Williamson
 */
@MetaInfServices
public class PrimitiveFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		factoryCollection.addFactory(boolean.class, new BooleanFactory(randomValueGenerator));
		factoryCollection.addFactory(byte.class, new ByteFactory(randomValueGenerator));
		factoryCollection.addFactory(short.class, new ShortFactory(randomValueGenerator));
		factoryCollection.addFactory(int.class, new IntegerFactory(randomValueGenerator));
		factoryCollection.addFactory(long.class, new LongFactory(randomValueGenerator));
		factoryCollection.addFactory(float.class, new FloatFactory(randomValueGenerator));
		factoryCollection.addFactory(double.class, new DoubleFactory(randomValueGenerator));
		factoryCollection.addFactory(char.class, new CharacterFactory(randomValueGenerator));
		factoryCollection.addFactory(void.class, () -> null);
	}
}
