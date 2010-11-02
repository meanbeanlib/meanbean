package org.meanbean.util;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleValidationHelperTest {

	private static final String NAME = "TEST_NAME";

	private static final String OPERATION = "TEST_OPERATION";

	private static final Object VALID_VALUE = "TEST_VALUE";

	@Mock
	private Log log;

	private SimpleValidationHelper simpleValidationHelper;

	@Before
	public void before() {
		simpleValidationHelper = new SimpleValidationHelper(log);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ensureExistsWithInvalidNameAndValue() {
		simpleValidationHelper.ensureExists(NAME, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ensureExistsWithInvalidNameAndValueAndOperation() {
		simpleValidationHelper.ensureExists(NAME, OPERATION, null);
	}

	@Test
	public void ensureExistsWithValidNameAndValue() {
		simpleValidationHelper.ensureExists(NAME, VALID_VALUE);
	}

	@Test
	public void ensureExistsWithValidNameAndValueAndOperation() {
		simpleValidationHelper.ensureExists(NAME, OPERATION, VALID_VALUE);
	}
}