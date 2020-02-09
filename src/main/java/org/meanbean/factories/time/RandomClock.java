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

package org.meanbean.factories.time;

import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueSampler;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Set;

public class RandomClock extends Clock {

	private RandomValueGenerator randomValueGenerator;
	private RandomValueSampler randomValueSampler;

	public RandomClock(RandomValueGenerator randomValueGenerator) {
		this.randomValueGenerator = randomValueGenerator;
		this.randomValueSampler = new RandomValueSampler(randomValueGenerator);
	}

	@Override
	public ZoneId getZone() {
		Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
		return randomValueSampler.findFrom(availableZoneIds)
				.map(ZoneId::of)
				.orElse(ZoneId.systemDefault());
	}

	@Override
	public Clock withZone(ZoneId zone) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Instant instant() {
		return Instant.ofEpochMilli(randomValueGenerator.nextLong());
	}

}
