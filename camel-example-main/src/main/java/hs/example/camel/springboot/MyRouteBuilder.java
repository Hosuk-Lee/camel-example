package hs.example.camel.springboot;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo").routeId("foo")
                .bean("myBean", "hello")
                .log("${body}");
    }
}
