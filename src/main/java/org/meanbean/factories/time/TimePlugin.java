package org.meanbean.factories.time;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.factories.FactoryCollectionPlugin;
import org.meanbean.factories.NoSuchFactoryException;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.function.Function;

public class TimePlugin implements FactoryCollectionPlugin {

	private RandomValueGenerator randomValueGenerator;
	private FactoryCollection factoryCollection;
	private Clock clock;

	public TimePlugin() {
		// empty
	}

	public TimePlugin(Clock clock) {
		this.clock = clock;
	}

	@Override
	public void initialize(FactoryCollection factoryCollection, RandomValueGenerator randomValueGenerator) {

		this.randomValueGenerator = randomValueGenerator;
		this.factoryCollection = factoryCollection;
		if (this.clock == null) {
			this.clock = new RandomClock(randomValueGenerator);
		}

		addFactory(Clock.class, () -> clock);
		addFactory(Instant.class, newFactory(Instant::now));
		addFactory(LocalDate.class, newFactory(LocalDate::now));
		addFactory(LocalDateTime.class, newFactory(LocalDateTime::now));
		addFactory(LocalTime.class, newFactory(LocalTime::now));
		addFactory(OffsetDateTime.class, newFactory(OffsetDateTime::now));
		addFactory(OffsetTime.class, newFactory(OffsetTime::now));
		addFactory(MonthDay.class, newFactory(MonthDay::now));
		addFactory(Year.class, newFactory(Year::now));
		addFactory(YearMonth.class, newFactory(YearMonth::now));
		addFactory(ZonedDateTime.class, newFactory(ZonedDateTime::now));
		addFactory(ZoneId.class, clock::getZone);
		addFactory(ZoneOffset.class, newZoneOffsetFactory());

		addFactory(Duration.class, newDurationFactory());
		addFactory(Period.class, newPeroidFactory());
	}

	private Factory<ZoneOffset> newZoneOffsetFactory() {
		return () -> {
			int sign = randomValueGenerator.nextBoolean() ? 1 : -1;
			int hours = randomValueGenerator.nextInt(18);
			int minutes = randomValueGenerator.nextInt(59);
			int seconds = randomValueGenerator.nextInt(59);
			return ZoneOffset.ofHoursMinutesSeconds(sign * hours, sign * minutes, sign * seconds);
		};
	}

	private Factory<Period> newPeroidFactory() {
		Factory<LocalDate> localDateFactory = getFactory(LocalDate.class);
		return () -> Period.between(localDateFactory.create(), localDateFactory.create());
	}

	private Factory<Duration> newDurationFactory() {
		Factory<LocalDateTime> localDateTimeFactory = getFactory(LocalDateTime.class);
		return () -> Duration.between(localDateTimeFactory.create(), localDateTimeFactory.create());
	}

	private <T> void addFactory(Class<T> clazz, Factory<T> factory) throws IllegalArgumentException {
		factoryCollection.addFactory(clazz, factory);
	}

	@SuppressWarnings("unchecked")
	private <T> Factory<T> getFactory(Class<T> clazz) throws IllegalArgumentException, NoSuchFactoryException {
		return (Factory<T>) factoryCollection.getFactory(clazz);
	}

	private <T> Factory<T> newFactory(Function<Clock, T> function) {
		return () -> function.apply(clock);
	}
}