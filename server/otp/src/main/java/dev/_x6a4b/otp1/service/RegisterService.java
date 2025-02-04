package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.auth.RegisterDto;
import dev._x6a4b.otp1.entity.Person;
import dev._x6a4b.otp1.entity.User;
import dev._x6a4b.otp1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    @Autowired
    public RegisterService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public boolean register(RegisterDto registerDto, Person person){
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent())
            return false;
        String pw = new BCryptPasswordEncoder().encode(registerDto.getPassword());
        User user = userRepository.save(new User(registerDto.getUsername(), pw, "user", person));
        return user.getId() != 0;
    }
}
