package com.xupu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class LocationphotobackgroundApplication  extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(LocationphotobackgroundApplication.class, args);
    }
    @RestController
    public class HelloController {
        @RequestMapping("/")
        public String hello() {



            return "Hello Spring Boot!";
        }
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(LocationphotobackgroundApplication.class);
    }


}
