package org.meanbean.factories.util;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class SimpleFactoryIdGeneratorTest {

	private SimpleFactoryIdGenerator simpleFactoryIdGenerator = new SimpleFactoryIdGenerator();

	@Test(expected=IllegalArgumentException.class)
    public void createIdFromClassWithInvalidClass() {
	    simpleFactoryIdGenerator.createIdFromClass(null);
    }
	
	@Test
    public void createIdFromClassWithString() {
	    String idFromClass = simpleFactoryIdGenerator.createIdFromClass(String.class);
	    assertThat("Incorrect ID created for String.", idFromClass, is(String.class.getName()));
	}
	
	@Test
    public void createIdFromClassWithLong() {
	    String idFromClass = simpleFactoryIdGenerator.createIdFromClass(Long.class);
	    assertThat("Incorrect ID created for Long.", idFromClass, is(Long.class.getName()));
	}
	
	@Test
    public void createIdFromClassWithInt() {
	    String idFromClass = simpleFactoryIdGenerator.createIdFromClass(int.class);
	    assertThat("Incorrect ID created for int.", idFromClass, is(int.class.getName()));
	}	
}