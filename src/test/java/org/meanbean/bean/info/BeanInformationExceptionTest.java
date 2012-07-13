package org.meanbean.bean.info;

import org.meanbean.test.util.MessageAndCauseExceptionTestBase;

public class BeanInformationExceptionTest extends MessageAndCauseExceptionTestBase {

    @Override
    public Exception createMessageException(String message) {
        return new BeanInformationException(message);
    }

    @Override
    public Exception createMessageAndCauseException(String message, Throwable cause) {
        return new BeanInformationException(message, cause);
    }
}