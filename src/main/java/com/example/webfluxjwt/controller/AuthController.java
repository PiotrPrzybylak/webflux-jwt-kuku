package com.example.webfluxjwt.controller;

import com.example.webfluxjwt.models.reqrespbodies.ReqLogin;
import com.example.webfluxjwt.models.reqrespmodel.ReqRespModel;
import com.example.webfluxjwt.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class AuthController {

    private final ReactiveUserDetailsService users;
    private final JWTService jwtService;
    private final PasswordEncoder encoder;

    @GetMapping("/auth")
    public Mono<ResponseEntity<ReqRespModel<String>>> auth() {
        return Mono.just(ResponseEntity.ok(new ReqRespModel<>("Welcome to the private club", "")));
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<ReqRespModel<String>>> login(@RequestBody ReqLogin user) {
        Mono<UserDetails> foundUser = users.findByUsername(user.getEmail()).defaultIfEmpty(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        });

        return foundUser.map(u -> {
            if (u.getUsername() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReqRespModel<>("", "User not found. Please register"));
            }

            if (encoder.matches(user.getPassword(), u.getPassword())) {
                return ResponseEntity.ok(new ReqRespModel<>(jwtService.generate(u.getUsername()), "Success"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ReqRespModel<>("", "Invalid Credentials"));
        });
    }
}
