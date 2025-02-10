package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.auth.LoginDto;
import dev._x6a4b.otp1.auth.RegisterDto;
import dev._x6a4b.otp1.entity.Person;

public interface AuthService {
    String login(LoginDto loginDto);
    boolean register(RegisterDto registerDto, Person person);

}
