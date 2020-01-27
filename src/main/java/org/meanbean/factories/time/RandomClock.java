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