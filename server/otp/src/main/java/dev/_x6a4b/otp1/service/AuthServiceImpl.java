package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.auth.JwtTokenProvider;
import dev._x6a4b.otp1.auth.LoginDto;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        System.out.println("authserviceimpl login " + loginDto.getUsername() + " : " + loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }
}
