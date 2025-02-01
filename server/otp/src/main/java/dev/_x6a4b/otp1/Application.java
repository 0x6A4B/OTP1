package dev._x6a4b.otp1;

import dev._x6a4b.otp1.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
//@EnableJpaRepositories
@ComponentScan({"dev._x6a4b.otp1.controller", "dev._x6a4b.otp1.service", "dev._x6a4b.otp1.repository"})
//@RestController
public class Application {
/*
    @RequestMapping("/")
    public String home() {
        return api();
    }

    @RequestMapping("/api")
    public String api() {
        System.out.println("/api from application.java");
        return "It works!!!";
    }

 */
/*
    @RequestMapping("/api/user")
    public String getUser(){
        return "user";
    }

    @RequestMapping("/api/user")
    public String postUser(){
        return "user";
    }
*/

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8088"));
        applicationContext = app.run(args);

        //checkBeansPresence("UserController", "UserService", "UserRepository");
    }

    private static void checkBeansPresence(String... beans) {
        for (String beanName : beans) {
            System.out.println("Is " + beanName + " in ApplicationContext: " +
                    applicationContext.containsBean(beanName));
        }
    }
}
