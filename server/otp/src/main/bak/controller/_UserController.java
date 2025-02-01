/*
package dev._x6a4b.otp1.controller;

import dev._x6a4b.otp1.dao.UserDao;
import dev._x6a4b.otp1.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class _UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public void login(@RequestBody String userName, String password){
        User user = userDao.findByName(userName);
        System.out.println("LOGIN: " + userName + " - " + password);

    }
}
*/