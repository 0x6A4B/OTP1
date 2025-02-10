package dev._x6a4b.otp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/")
    public String home() {
        return api();
    }

    @RequestMapping("/api")
    public String api() {
        return "It works!!!";
    }

    @RequestMapping("/api/user")
    public String getUser(){
        return "user";
    }

    @RequestMapping("/api/user")
    public String postUser(){
        return "user";
    }

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8088"));
        app.run(args);
    }
}
