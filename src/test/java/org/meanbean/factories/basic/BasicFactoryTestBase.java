package org.meanbean.factories.basic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.util.RandomValueGenerator;

public abstract class BasicFactoryTestBase<T> {

	@Test(expected = IllegalArgumentException.class)
    public void constructorShouldPreventNullRandomNumberGenerator() throws Exception {
	    createFactory(null);
    }
	
	@Test
    public void createShouldReturnNewObjectEachInvocation() throws Exception {
	    Factory<T> factory = createFactory(createRandomNumberGenerator());
	    T createdObject1 = factory.create();
	    T createdObject2 = factory.create();
	    assertThat("Factory does not create new objects.", createdObject1, is(not(sameInstance(createdObject2))));
    }
	
	@Test
    public void createShouldReturnDifferentValuesEachInvocation() throws Exception {
		Factory<T> factory = createFactory(createRandomNumberGenerator());
	    T createdObject1 = factory.create();
	    T createdObject2 = factory.create();
	    assertThat("Factory does not create different values.", createdObject1, is(not(createdObject2)));
    }

	protected abstract RandomValueGenerator createRandomNumberGenerator();

	protected abstract Factory<T> createFactory(RandomValueGenerator randomValueGenerator);
}