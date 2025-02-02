package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    // for testing
    public User getUser(Long id){
        //return userRepository.find(id);
        return new User("tset", "test", "test");
    }
    public Optional<User> getUserById(Long id){
        System.out.println("userservice.getuserbyid: " + id);
        return userRepository.findById(id);
    }

    public Optional<User> getUserByName(String username){
        System.out.println("userservice.getuserbyname: " + username);

        return userRepository.findByUsername(username);
    }

    public List<User> getUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUser(String username){
        //return userRepository.findByUserName(username);
        return new User();
    }



}
