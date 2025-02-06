package dev._x6a4b.otp1.controller;

import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getUser(){
        System.out.println("arrived in controller.userservice /api/user/");
        return "FAK";
    }

    @GetMapping("/all")
    public List<User> getUsers(){
        System.out.println("Arrived in to controller.userService: getall");
        List<User> users = userService.getUsers();
        System.out.println(users);
        return users;
        //System.out.println("user: " + user.toString());
        //return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //return userService.getUser(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        System.out.println("Arrived in to controller.userService");
        Optional<User> user = userService.getUserById(id);
        System.out.println("user: " + user.toString());
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //return userService.getUser(id);
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable String username){
        System.out.println("Arrived in to controller.userService");
        Optional<User> user = userService.getUserByName(username);
        System.out.println("user: " + user.toString());
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //return userService.getUser(id);
    }


}
