package org.meanbean.test.beans;

import org.meanbean.lang.Factory;

/**
 * Factory wrapper (decorator) that keeps count of the number of times <code>create()</code> is invoked, delegating the
 * actual creation of the object to the wrapped factory, specified at construction.
 * 
 * This should only be used for testing.
 * 
 * @author Graham Williamson
 */
public class InvocationCountingFactoryWrapper<T> implements Factory<T> {

	private final Factory<T> factory;

	private int invocationCount = 0;

	public InvocationCountingFactoryWrapper(Factory<T> factory) {
		this.factory = factory;
	}

	public T create() {
		invocationCount++;
		return factory.create();
	}

	public int getInvocationCount() {
		return invocationCount;
	}
}