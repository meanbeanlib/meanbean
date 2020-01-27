package org.meanbean.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.RandomAccess;

public class RandomValueSampler {

	private RandomValueGenerator randomValueGenerator;

	public RandomValueSampler(RandomValueGenerator randomValueGenerator) {
		this.randomValueGenerator = randomValueGenerator;
	}

	public <E> E getFrom(Collection<E> collection) {
		return findFrom(collection)
				.orElseThrow(() -> new IllegalStateException());
	}

	public <E> Optional<E> findFrom(Collection<E> collection) {
		if (collection instanceof List && collection instanceof RandomAccess) {
			return findFrom((List<E>) collection);
		}
		return collection.stream()
				.skip(randomIndex(collection))
				.findFirst();
	}

	public <E> E getFrom(List<E> list) {
		return findFrom(list)
				.orElseThrow(() -> new IllegalStateException());
	}

	public <E> Optional<E> findFrom(List<E> list) {
		if (list.isEmpty()) {
			return Optional.empty();
		}

		int randomSkip = randomIndex(list);
		return Optional.ofNullable(list.get(randomSkip));
	}

	private <E> int randomIndex(Collection<E> collection) {
		return collection.size() == 0
				? 0
				: randomValueGenerator.nextInt(collection.size());
	}
}