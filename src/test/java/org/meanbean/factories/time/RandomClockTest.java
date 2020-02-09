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

import org.junit.Test;
import org.meanbean.util.SimpleRandomValueGenerator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomClockTest {

	private int count = 10;
	private RandomClock randomClock = new RandomClock(new SimpleRandomValueGenerator());

	@Test
	public void testGetZone() {
		List<ZoneId> zones = nUniqueCopies(randomClock::getZone);

		assertThat(zones)
				.allMatch(this::isValidZone)
				.hasSizeGreaterThan(5);
	}

	@Test
	public void testGetInstant() {
		List<Instant> instants = nUniqueCopies(randomClock::instant);

		assertThat(instants)
				.hasSize(10);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testWithZone() {
		randomClock.withZone(ZoneOffset.UTC);
	}

	private <T> List<T> nUniqueCopies(Supplier<T> supplier) {
		return IntStream.range(0, count)
				.mapToObj(num -> supplier.get())
				.distinct()
				.collect(Collectors.toList());
	}

	private boolean isValidZone(ZoneId zoneId) {
		return ZoneId.getAvailableZoneIds().contains(zoneId.getId());
	}
}
