package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.entity.User;

import dev._x6a4b.otp1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // for testing
    public User getUser(long id){
        return userRepository.find(id);
    }
    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public User getUser(String username){
        return userRepository.findByName(username);
    }



}
