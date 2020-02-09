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

package org.meanbean.factories.util;

import org.kohsuke.MetaInfServices;
import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryCollectionPlugin;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueSampler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@MetaInfServices(FactoryCollectionPlugin.class)
public class LocaleFactory implements Factory<Locale>, FactoryCollectionPlugin {

	private RandomValueGenerator randomValueGenerator = RandomValueGenerator.getInstance();
	private RandomValueSampler randomValueSampler = new RandomValueSampler(randomValueGenerator);

	@Override
	public Locale create() {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
		return randomValueSampler.getFrom(locales);
	}

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {
		factoryCollection.addFactory(Locale.class, this);
	}
}
