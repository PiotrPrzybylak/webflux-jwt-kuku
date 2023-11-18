package com.example.webfluxjwt.controller;

import com.example.webfluxjwt.models.reqrespbodies.ReqLogin;
import com.example.webfluxjwt.models.reqrespmodel.ReqRespModel;
import com.example.webfluxjwt.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AuthController {

    private final ReactiveUserDetailsService users;
    private final JWTService jwtService;

    @GetMapping("/auth")
    public Mono<ResponseEntity<ReqRespModel<String>>> auth() {
        return Mono.just(ResponseEntity.ok(new ReqRespModel<>("Welcome to the private club", ""
        )));
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<ReqRespModel<String>>> login(@RequestBody ReqLogin user) {
        Mono<UserDetails> foundUser = users.findByUsername(user.getEmail()).defaultIfEmpty(null);

        return foundUser.flatMap(u -> {
            if (u != null) {
                if (u.getPassword().equals(user.getPassword())) {
                    return Mono.just(
                            ResponseEntity.ok(
                                    new ReqRespModel<>(jwtService.generate(u.getUsername()), "Success")
                            )
                    );
                }
                return Mono.just(
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ReqRespModel<>("", "Invalid Credentials"))
                );
            }
            return Mono.just(
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ReqRespModel<>("", "User not found. Please register"))
            );
        });
    }
}
