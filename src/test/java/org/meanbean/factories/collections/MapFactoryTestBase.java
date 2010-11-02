package org.meanbean.factories.collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.Factory;
import org.meanbean.factories.basic.ArrayBasedRandomNumberGenerator;
import org.meanbean.factories.basic.LongFactory;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomNumberGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class MapFactoryTestBase {

	private static final long[] RANDOM_LONGS = new long[] { 1, 2, -3, 4, 5, -6, -7, -8, 9, 10, -11, -12 };

	@Mock
	protected RandomNumberGenerator randomNumberGenerator;

	@Mock
	protected Factory<String> keyFactory;

	@Mock
	protected Factory<Long> valueFactory;

	@Test
	public void createMapShouldReturnCorrectMapType() throws Exception {
		MapFactoryBase<String, Long> factory = getMapFactory(randomNumberGenerator, keyFactory, valueFactory);
		Map<String, Long> expectedType = getMapOfExpectedType();
		Map<String, Long> createdType = factory.createMap();
		assertThat("Incorrect type of Map created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createMapShouldReturnNewMapInstanceEachInvocation() throws Exception {
		MapFactoryBase<String, Long> factory = getMapFactory(randomNumberGenerator, keyFactory, valueFactory);
		assertThat("Should be different instances.", factory.createMap(), is(not(sameInstance(factory.createMap()))));
	}

	@Test
	public void createShouldReturnCorrectMapType() throws Exception {
		MapFactoryBase<String, Long> factory = getMapFactory(randomNumberGenerator, keyFactory, valueFactory);
		Map<String, Long> expectedType = getMapOfExpectedType();
		Map<String, Long> createdType = factory.create();
		assertThat("Incorrect type of Map created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createShouldReturnNewMapInstanceEachInvocation() throws Exception {
		MapFactoryBase<String, Long> factory = getMapFactory(randomNumberGenerator, keyFactory, valueFactory);
		assertThat("Should be different instances.", factory.create(), is(not(sameInstance(factory.create()))));
	}

	@Test
	public void createShouldReturnExpectedSizeOfMap() throws Exception {
		RandomNumberGenerator randomNumberGenerator = new ArrayBasedRandomNumberGenerator(null, null, RANDOM_LONGS,
		        null, new double[] { 0.0321 }, null);
		Factory<String> keyFactory = new StringFactory(randomNumberGenerator);
		Factory<Long> valueFactory = new LongFactory(randomNumberGenerator);
		MapFactoryBase<String, Long> factory = getMapFactory(randomNumberGenerator, keyFactory, valueFactory);
		assertThat("Incorrect Map created.", factory.create().size(), is(3));
	}
	
	@Test
	public void createShouldReturnExpectedMapContents() throws Exception {
		RandomNumberGenerator randomNumberGenerator = new ArrayBasedRandomNumberGenerator(null, null, RANDOM_LONGS,
		        null, new double[] { 0.06 }, null);
		Factory<String> keyFactory = new StringFactory(randomNumberGenerator);
		Factory<Long> valueFactory = new LongFactory(randomNumberGenerator);
		MapFactoryBase<String, Long> factory = getMapFactory(randomNumberGenerator, keyFactory, valueFactory);
		Map<String, Long> expectedMap = factory.createMap();
		for (int idx = 0; idx < RANDOM_LONGS.length;) {
			expectedMap.put("TestString:[" + RANDOM_LONGS[idx++] + "]", RANDOM_LONGS[idx++]);
		}
		Map<String, Long> createdMap = factory.create();
		assertThat("Incorrect Map created.", new TreeMap<String, Long>(createdMap), is(new TreeMap<String, Long>(
		        expectedMap)));
	}

	protected abstract MapFactoryBase<String, Long> getMapFactory(RandomNumberGenerator randomNumberGenerator,
	        Factory<String> keyFactory, Factory<Long> valueFactory);

	protected abstract Map<String, Long> getMapOfExpectedType();
}