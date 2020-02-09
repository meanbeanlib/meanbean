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
import org.meanbean.factories.basic.BigDecimalFactory;
import org.meanbean.factories.basic.BigIntegerFactory;
import org.meanbean.factories.basic.BooleanFactory;
import org.meanbean.factories.basic.ByteFactory;
import org.meanbean.factories.basic.CharacterFactory;
import org.meanbean.factories.basic.DateFactory;
import org.meanbean.factories.basic.DoubleFactory;
import org.meanbean.factories.basic.FloatFactory;
import org.meanbean.factories.basic.IntegerFactory;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.ShortFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomValueGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

/**
 * Concrete FactoryCollectionPlugin that registers Factories that create basic Java wrapper objects.
 * 
 * @author Graham Williamson
 */
@MetaInfServices
public class ObjectFactoryPlugin implements FactoryCollectionPlugin {

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		factoryCollection.addFactory(Boolean.class, new BooleanFactory(randomValueGenerator));
		factoryCollection.addFactory(Byte.class, new ByteFactory(randomValueGenerator));
		factoryCollection.addFactory(Short.class, new ShortFactory(randomValueGenerator));
		factoryCollection.addFactory(Integer.class, new IntegerFactory(randomValueGenerator));
		factoryCollection.addFactory(Long.class, new LongFactory(randomValueGenerator));
		factoryCollection.addFactory(Float.class, new FloatFactory(randomValueGenerator));
		factoryCollection.addFactory(Double.class, new DoubleFactory(randomValueGenerator));
		factoryCollection.addFactory(BigDecimal.class, new BigDecimalFactory(randomValueGenerator));
		factoryCollection.addFactory(BigInteger.class, new BigIntegerFactory(randomValueGenerator));
		factoryCollection.addFactory(Character.class, new CharacterFactory(randomValueGenerator));
		factoryCollection.addFactory(String.class, new StringFactory(randomValueGenerator));
		factoryCollection.addFactory(Void.TYPE, () -> null);
		factoryCollection.addFactory(Date.class, new DateFactory(randomValueGenerator));
		factoryCollection.addFactory(UUID.class, UUID::randomUUID);
		
	}
}
