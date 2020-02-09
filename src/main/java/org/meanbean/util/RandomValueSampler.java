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
