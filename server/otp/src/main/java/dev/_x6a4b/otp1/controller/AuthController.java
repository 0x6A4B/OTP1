package dev._x6a4b.otp1.controller;

import dev._x6a4b.otp1.auth.JwtAuthResponse;
import dev._x6a4b.otp1.auth.LoginDto;
import dev._x6a4b.otp1.auth.RegisterDto;
import dev._x6a4b.otp1.entity.Person;
import dev._x6a4b.otp1.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        System.out.println("authcontroller register");
        System.out.println(registerDto.getUsername() + " : " + registerDto.getPassword());
        System.out.println("person: " + registerDto.getPerson().getFirstName());
        boolean result = authService.register(registerDto, registerDto.getPerson());
        if (!result)
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
