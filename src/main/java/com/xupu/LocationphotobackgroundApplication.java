package com.xupu;

import com.xupu.cbd.dao.UserRepository;
import com.xupu.cbd.service.UserRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootApplication
public class LocationphotobackgroundApplication {

    @Autowired
    private UserRepositoryTest userRepositoryTest;
    public static void main(String[] args) {
        SpringApplication.run(LocationphotobackgroundApplication.class, args);
    }
    @RestController
    public class HelloController {
        @RequestMapping("/hello")
        public String hello() {

            userRepositoryTest.test();

            return "Hello Spring Boot!";
        }
    }
}
