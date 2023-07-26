package hs.example.camel;

public class MyConfiguration {

    @BindToRegistry
    public MyBean myBean(@PropertyInject("hi") String hi, @PropertyInject("bye") String bye) {
        // this will create an instance of this bean with the name of the method (eg myBean)
        return new MyBean(hi, bye);
    }

    @Override
    public void configure(CamelContext camelContext) {
        // this method is optional and can be removed if no additional configuration is needed.
    }
}
