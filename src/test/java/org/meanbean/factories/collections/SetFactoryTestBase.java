package org.meanbean.factories.collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.factories.basic.ArrayBasedRandomValueGenerator;
import org.meanbean.factories.basic.StringFactory;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class SetFactoryTestBase {

	@Mock
	protected RandomValueGenerator randomValueGenerator;

	@Mock
	protected Factory<String> itemFactory;

	private static final long[] RANDOM_LONGS = new long[] { 1, -3, 5, -7, 9, 10 };

	@Test
	public void createSetShouldReturnCorrectSetType() throws Exception {
		SetFactoryBase<String> factory = getSetFactory(randomValueGenerator, itemFactory);
		Set<String> expectedType = getSetOfExpectedType();
		Set<String> createdType = factory.createSet();
		assertThat("Incorrect type of Set created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createSetShouldReturnNewSetInstanceEachInvocation() throws Exception {
		SetFactoryBase<String> factory = getSetFactory(randomValueGenerator, itemFactory);
		assertThat("Should be different instances.", factory.createSet(), is(not(sameInstance(factory.createSet()))));
	}

	@Test
	public void createShouldReturnCorrectSetType() throws Exception {
		SetFactoryBase<String> factory = getSetFactory(randomValueGenerator, itemFactory);
		Set<String> expectedType = getSetOfExpectedType();
		Set<String> createdType = factory.create();
		assertThat("Incorrect type of Set created.", createdType.getClass().getName(), is(expectedType.getClass()
		        .getName()));
	}

	@Test
	public void createShouldReturnNewSetInstanceEachInvocation() throws Exception {
		SetFactoryBase<String> factory = getSetFactory(randomValueGenerator, itemFactory);
		assertThat("Should be different instances.", factory.create(), is(not(sameInstance(factory.create()))));
	}

	@Test
	public void createShouldReturnExpectedSizeOfSet() throws Exception {
		RandomValueGenerator randomValueGenerator = new ArrayBasedRandomValueGenerator(null, null, RANDOM_LONGS,
		        null, new double[] { 0.0421 }, null);
		Factory<String> itemFactory = new StringFactory(randomValueGenerator);
		SetFactoryBase<String> factory = getSetFactory(randomValueGenerator, itemFactory);
		assertThat("Incorrect Set created.", factory.create().size(), is(4));
	}

	@Test
	public void createShouldReturnExpectedSetContents() throws Exception {
		RandomValueGenerator randomValueGenerator = new ArrayBasedRandomValueGenerator(null, null, RANDOM_LONGS,
		        null, new double[] { 0.06 }, null);
		Factory<String> itemFactory = new StringFactory(randomValueGenerator);
		SetFactoryBase<String> factory = getSetFactory(randomValueGenerator, itemFactory);
		Set<String> expectedSet = factory.createSet();
		for (int idx = 0; idx < RANDOM_LONGS.length;) {
			expectedSet.add("TestString:[" + RANDOM_LONGS[idx++] + "]");
		}
		Set<String> createdSet = factory.create();
		assertThat("Incorrect Map created.", new TreeSet<String>(createdSet), is(new TreeSet<String>(expectedSet)));
	}

	protected abstract SetFactoryBase<String> getSetFactory(RandomValueGenerator randomValueGenerator,
	        Factory<String> itemFactory);

	protected abstract Set<String> getSetOfExpectedType();
}