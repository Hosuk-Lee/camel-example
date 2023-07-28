package hs.example.camel;

import org.apache.camel.BindToRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.Configuration;
import org.apache.camel.PropertyInject;
import org.apache.camel.CamelConfiguration;

/**
 * Class to configure the Camel application.
 */
@Configuration
public class MyConfiguration {
    @BindToRegistry
    public MyBean myBean(@PropertyInject("hi") String hi) {
        // this will create an instance of this bean with the name of the method (eg myBean)
        return new MyBean(hi);
    }
}
