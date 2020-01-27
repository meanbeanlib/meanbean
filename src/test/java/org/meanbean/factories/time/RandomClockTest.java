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