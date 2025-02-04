package dev._x6a4b.otp1.configuration;

import dev._x6a4b.otp1.auth.JwtAuthenticationEntryPoint;
import dev._x6a4b.otp1.auth.JwtAuthenticationFilter;
import dev._x6a4b.otp1.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;


    public SpringSecurityConfig(JwtAuthenticationFilter authenticationFilter, UserDetailsService userDetailsService, JwtAuthenticationEntryPoint authenticationEntryPoint){
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(crsf -> crsf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/*").permitAll();
                    authorize.requestMatchers("/api/*").permitAll();
                    authorize.requestMatchers("/api/user/**").permitAll();  // to test for it functioning!
                    //authorize.requestMatchers("/api/device").hasRole("USER");   // for devices
                    authorize.requestMatchers("/api/auth/**").permitAll();  // for login and registering
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();   // ? why this ?
                    //authorize.requestMatchers("/api/device/**").permitAll();
                    authorize.anyRequest().authenticated(); // for those who are authenticated?
                }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
