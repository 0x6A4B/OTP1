package dev._x6a4b.otp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
