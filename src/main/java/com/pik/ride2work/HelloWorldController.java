package com.pik.ride2work;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HelloWorldController {

    private static final String HELLO_TEMPLATE = "Hello, %s!";
    private static final String BYE_TEMPLATE = "Bye, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(HELLO_TEMPLATE, name));
    }

    @GetMapping("/bye")
    @ResponseBody
    public Greeting sayGoodBye(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(BYE_TEMPLATE, name));
    }
}
