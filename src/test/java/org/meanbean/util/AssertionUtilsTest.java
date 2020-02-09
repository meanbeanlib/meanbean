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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AssertionUtilsTest {

    @Test(expected = AssertionError.class)
    public void shouldThrowAssertionError() throws Exception {
        // Given
        // When
        AssertionUtils.fail();
        // Then - should throw AssertionError
    }

    @Test
    public void shouldThrowAssertionErrorWithMessage() throws Exception {
        // Given
        final String message = "TEST_FAIL_MESSAGE";
        // When
        AssertionError assertionError = null;
        try {
            AssertionUtils.fail(message);
        } catch (AssertionError error) {
            assertionError = error;
        }
        // Then
        assertThat("fail should have thrown AssertionError.", assertionError, is(not(nullValue())));
        assertThat("Incorrect message.", assertionError.getMessage(), is(message));
    }
}
