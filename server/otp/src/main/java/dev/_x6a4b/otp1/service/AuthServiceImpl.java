package dev._x6a4b.otp1.service;

import dev._x6a4b.otp1.auth.JwtTokenProvider;
import dev._x6a4b.otp1.auth.LoginDto;

import dev._x6a4b.otp1.auth.RegisterDto;
import dev._x6a4b.otp1.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;
    private final RegisterService registerService;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, RegisterService registerService){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

        this.userDetailsService = userDetailsService;
        this.registerService = registerService;
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

    public boolean register(RegisterDto registerDto){
        System.out.println("registering " + registerDto.getUsername() + " : " + registerDto.getPassword());
//        boolean userExists = registerService.userExists(registerDto);
//        System.out.println("Username exists: " + userExists);
//
//        if(userExists)
//            return null;

        boolean success = registerService.register(registerDto);

        return success;
    }
}
