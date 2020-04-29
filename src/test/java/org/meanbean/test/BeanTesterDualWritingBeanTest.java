package org.meanbean.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.meanbean.test.Warning.SETTER_SIDE_EFFECT;

public class BeanTesterDualWritingBeanTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDualWriteFails() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Property [name] appears to have a side-effect on another property [title]");

        BeanVerifier.forClass(DualWritingSetterBean.class)
                .verifyGettersAndSetters();
    }

    @Test
    public void testDualWriteSuppressed() {
        BeanVerifier.forClass(DualWritingSetterBean.class)
                .withSettings(settings -> settings.suppressWarning(SETTER_SIDE_EFFECT))
                .verifyGettersAndSetters();
    }

    public static class DualWritingSetterBean {
        private String name;
        private String title;

        public String getName() {
            return name;
        }

        // the setter writes to two fields
        public void setName(String name) {
            this.name = name;
            this.title = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
