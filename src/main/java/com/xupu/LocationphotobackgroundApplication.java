package com.xupu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)  extends SpringBootServletInitializer
@SpringBootApplication
public class LocationphotobackgroundApplication  {


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
    // 用于构建war文件并进行部署
  /*  @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }*/

}
