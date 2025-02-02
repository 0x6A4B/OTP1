package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.auth.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
