package hs.example.camel.springboot;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.main.junit5.CamelMainTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A unit test using the legacy approach checking that Camel supports binding via annotations.
 */
class MainTest extends CamelMainTestSupport {

    @Test
    void should_support_binding_via_annotations() {
        NotifyBuilder notify = new NotifyBuilder(context)
                .whenCompleted(1).whenBodiesDone("Hello how are you?").create();
        assertTrue(
                notify.matches(20, TimeUnit.SECONDS), "1 message should be completed"
        );
    }

    @Override
    protected Class<?> getMainClass() {
        return MyApplication.class;
    }
}