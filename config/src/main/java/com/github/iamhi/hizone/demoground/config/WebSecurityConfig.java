package com.github.iamhi.hizone.demoground.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private static final String[] NOT_AUTHORIZE_PATHS = {
        "/actuator/**",
        "/error",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/**"
    };

    private static final String[] PERMITTED_POST_PATHS = {"/hello"};

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.inMemoryAuthentication().withUser("admin")
            .password(passwordEncoder.encode("admin123"))
            .authorities("basic", "admin");

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(requestCustomizer ->
                requestCustomizer.requestMatchers(NOT_AUTHORIZE_PATHS)
                    .permitAll()
                    .requestMatchers(PERMITTED_POST_PATHS)
                    .permitAll()
                    .requestMatchers("/hello/secret")
                    .fullyAuthenticated()
                    .anyRequest()
                    .fullyAuthenticated())
            .httpBasic(customizer -> customizer.authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
