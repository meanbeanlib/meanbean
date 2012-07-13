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