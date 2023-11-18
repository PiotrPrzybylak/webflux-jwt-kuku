package com.example.webfluxjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @GetMapping("/auth")
    public Mono<String> auth() {
        return Mono.just("Hello welcome to the club");
    }


    @PostMapping("/login")
    public Mono<String> login() {
        return Mono.just("Login page");
    }
}
