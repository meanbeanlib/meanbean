package org.meanbean.factories.beans;

import org.meanbean.test.util.MessageAndCauseExceptionTestBase;

public class BeanCreationExceptionTest extends MessageAndCauseExceptionTestBase {

    @Override
    public Exception createMessageException(String message) {
        return new BeanCreationException(message);
    }

    @Override
    public Exception createMessageAndCauseException(String message, Throwable cause) {
        return new BeanCreationException(message, cause);
    }
}