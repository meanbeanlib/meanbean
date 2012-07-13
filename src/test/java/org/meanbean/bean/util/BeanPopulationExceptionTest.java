package org.meanbean.bean.util;

import org.meanbean.test.util.MessageAndCauseExceptionTestBase;

public class BeanPopulationExceptionTest extends MessageAndCauseExceptionTestBase {

    @Override
    public Exception createMessageException(String message) {
        return new BeanPopulationException(message);
    }

    @Override
    public Exception createMessageAndCauseException(String message, Throwable cause) {
        return new BeanPopulationException(message, cause);
    }
}