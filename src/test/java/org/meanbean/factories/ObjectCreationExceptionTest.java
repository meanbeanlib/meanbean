package org.meanbean.factories;

import org.meanbean.test.util.MessageAndCauseExceptionTestBase;

public class ObjectCreationExceptionTest extends MessageAndCauseExceptionTestBase {

    @Override
    public Exception createMessageException(String message) {
        return new ObjectCreationException(message);
    }

    @Override
    public Exception createMessageAndCauseException(String message, Throwable cause) {
        return new ObjectCreationException(message, cause);
    }
}