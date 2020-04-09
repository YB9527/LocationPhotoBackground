package com.xupu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class LocationphotobackgroundApplication {


    public static void main(String[] args) {
        SpringApplication.run(LocationphotobackgroundApplication.class, args);
    }
    @RestController
    public class HelloController {
        @RequestMapping("/hello")
        public String hello() {



            return "Hello Spring Boot!";
        }
    }
}
