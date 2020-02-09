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

package org.meanbean.test;

import org.meanbean.test.util.MessageAndCauseExceptionTestBase;

public class BeanTestExceptionTest extends MessageAndCauseExceptionTestBase {

    @Override
    public Exception createMessageException(String message) {
        return new BeanTestException(message);
    }

    @Override
    public Exception createMessageAndCauseException(String message, Throwable cause) {
        return new BeanTestException(message, cause);
    }
}
