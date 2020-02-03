package org.meanbean.factories.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.basic.ArrayBasedRandomValueGenerator;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

@RunWith(MockitoJUnitRunner.class)
public abstract class ListFactoryTestBase {

	@Mock
	protected RandomValueGenerator randomValueGenerator;

	@Mock
	protected Factory<String> itemFactory;

	private static final long[] RANDOM_LONGS = new long[] { 1, -3, 5, -7, 9, 10 };
	private static final int[] RANDOM_INTS = LongStream.of(RANDOM_LONGS)
			.mapToInt(longValue -> (int) longValue)
			.toArray();

	@Test
	public void createListShouldReturnCorrectListType() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomValueGenerator, itemFactory);
		List<String> expectedType = getListOfExpectedType();
		List<String> createdType = factory.createList();
		assertThat("Incorrect type of List created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createListShouldReturnNewListInstanceEachInvocation() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomValueGenerator, itemFactory);
		assertThat("Should be different instances.", factory.createList(), is(not(sameInstance(factory.createList()))));
	}

	@Test
	public void createShouldReturnCorrectListType() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomValueGenerator, itemFactory);
		List<String> expectedType = getListOfExpectedType();
		List<String> createdType = factory.create();
		assertThat("Incorrect type of List created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createShouldReturnNewListInstanceEachInvocation() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomValueGenerator, itemFactory);
		assertThat("Should be different instances.", factory.create(), is(not(sameInstance(factory.create()))));
	}

	@Test
	public void createShouldReturnExpectedSizeOfList() throws Exception {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, RANDOM_INTS, RANDOM_LONGS, null, new double[] { 0.0421 }, null);
		Factory<String> itemFactory = new StringFactory(randomValueGenerator);
		ListFactoryBase<String> factory = getListFactory(randomValueGenerator, itemFactory);
		assertThat("Incorrect List created.", factory.create().size(), is(2));
	}

	@Test
	public void createShouldReturnExpectedListContents() throws Exception {
		RandomValueGenerator randomValueGenerator =
		        new ArrayBasedRandomValueGenerator(null, RANDOM_INTS, RANDOM_LONGS, null, new double[] { 0.06 }, null);
		Factory<String> itemFactory = new StringFactory(randomValueGenerator);
		ListFactoryBase<String> factory = getListFactory(randomValueGenerator, itemFactory);
		List<String> expectedList = factory.createList();
		expectedList.add("TestString:[-3]");
		expectedList.add("TestString:[1]");
		List<String> createdList = factory.create();
		Collections.sort(createdList);
		Collections.sort(expectedList);
		assertThat("Incorrect Map created.", createdList, is(expectedList));
	}

	protected abstract ListFactoryBase<String> getListFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory);

	protected abstract List<String> getListOfExpectedType();
}