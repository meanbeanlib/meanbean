package org.meanbean.factories.collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.Factory;
import org.meanbean.factories.basic.ArrayBasedRandomNumberGenerator;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.util.RandomNumberGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class ListFactoryTestBase {

	@Mock
	protected RandomNumberGenerator randomNumberGenerator;

	@Mock
	protected Factory<String> itemFactory;

	private static final long[] RANDOM_LONGS = new long[] { 1, -3, 5, -7, 9, 10 };

	@Test
	public void createListShouldReturnCorrectListType() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomNumberGenerator, itemFactory);
		List<String> expectedType = getListOfExpectedType();
		List<String> createdType = factory.createList();
		assertThat("Incorrect type of List created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createListShouldReturnNewListInstanceEachInvocation() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomNumberGenerator, itemFactory);
		assertThat("Should be different instances.", factory.createList(), is(not(sameInstance(factory.createList()))));
	}

	@Test
	public void createShouldReturnCorrectListType() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomNumberGenerator, itemFactory);
		List<String> expectedType = getListOfExpectedType();
		List<String> createdType = factory.create();
		assertThat("Incorrect type of List created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createShouldReturnNewListInstanceEachInvocation() throws Exception {
		ListFactoryBase<String> factory = getListFactory(randomNumberGenerator, itemFactory);
		assertThat("Should be different instances.", factory.create(), is(not(sameInstance(factory.create()))));
	}

	@Test
	public void createShouldReturnExpectedSizeOfList() throws Exception {
		RandomNumberGenerator randomNumberGenerator = new ArrayBasedRandomNumberGenerator(null, null, RANDOM_LONGS,
		        null, new double[] { 0.0421 }, null);
		Factory<String> itemFactory = new StringFactory(randomNumberGenerator);
		ListFactoryBase<String> factory = getListFactory(randomNumberGenerator, itemFactory);
		assertThat("Incorrect List created.", factory.create().size(), is(4));
	}

	@Test
	public void createShouldReturnExpectedListContents() throws Exception {
		RandomNumberGenerator randomNumberGenerator = new ArrayBasedRandomNumberGenerator(null, null, RANDOM_LONGS,
		        null, new double[] { 0.06 }, null);
		Factory<String> itemFactory = new StringFactory(randomNumberGenerator);
		ListFactoryBase<String> factory = getListFactory(randomNumberGenerator, itemFactory);
		List<String> expectedList = factory.createList();
		for (int idx = 0; idx < RANDOM_LONGS.length;) {
			expectedList.add("TestString:[" + RANDOM_LONGS[idx++] + "]");
		}
		List<String> createdList = factory.create();
		Collections.sort(createdList);
		Collections.sort(expectedList);
		assertThat("Incorrect Map created.", createdList, is(expectedList));
	}

	protected abstract ListFactoryBase<String> getListFactory(RandomNumberGenerator randomNumberGenerator,
	        Factory<String> itemFactory);

	protected abstract List<String> getListOfExpectedType();
}