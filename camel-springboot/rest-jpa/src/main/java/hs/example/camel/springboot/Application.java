package hs.example.camel.springboot;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            restConfiguration()
                    .component("servlet")
                    .contextPath("/camel-rest-jpa").apiContextPath("/api-doc")
                    .apiProperty("api.title", "Camel REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true")
                    .port(env.getProperty("server.port", "8080"))
                    .bindingMode(RestBindingMode.json);

            rest("/books").description("Books REST service")
                    .get("/").description("The list of all the books").to("direct:books")
                    .get("order/{id}").description("Details of an order by id").to("direct:order");

            from("direct:books").routeId("books-api")
                    .bean(Database.class, "findBooks");

            from("direct:order").routeId("order-api")
                    .bean(Database.class, "findOrder(${header.id})");
        }
    }

    @Component
    class Backend extends RouteBuilder {

        @Override
        public void configure() {
            // A first route generates some orders and queue them in DB
            from("timer:new-order?delay=1000&period={{example.generateOrderPeriod:2000}}")
                    .routeId("generate-order")
                    .bean("orderService", "generateOrder")
                    .to("jpa:hs.example.camel.springboot.Order")
                    .log("Inserted new order ${body.id}");

            // A second route polls the DB for new orders and processes them
            from("jpa:hs.example.camel.springboot.Order"
                    + "?namedQuery=new-orders"
                    + "&delay={{example.processOrderPeriod:5000}}"
                    + "&consumeDelete=false")
                    .routeId("process-order")
                    .log("Processed order #id ${body.id} with ${body.amount} copies of the «${body.book.description}» book");
        }
    }
}