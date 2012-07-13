package org.meanbean.factories;

import org.meanbean.test.util.MessageAndCauseExceptionTestBase;

public class NoSuchFactoryExceptionTest extends MessageAndCauseExceptionTestBase {

    @Override
    public Exception createMessageException(String message) {
        return new NoSuchFactoryException(message);
    }

    @Override
    public Exception createMessageAndCauseException(String message, Throwable cause) {
        return new NoSuchFactoryException(message, cause);
    }
}