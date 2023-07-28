package hs.example.camel.springboot;

import java.util.concurrent.TimeUnit;

import org.apache.camel.BeanInject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.main.junit5.CamelMainTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A unit test using the annotation based approach checking that Camel supports binding via annotations.
 */
@CamelMainTest(mainClass = MyApplication.class)
class MainWithAnnotationTest {

    @BeanInject
    CamelContext context;

    @Test
    void should_support_binding_via_annotations() {
        NotifyBuilder notify = new NotifyBuilder(context)
                .whenCompleted(1).whenBodiesDone("Hello how are you?").create();
        assertTrue(
                notify.matches(20, TimeUnit.SECONDS), "1 message should be completed"
        );
    }
}