package com.example.webfluxjwt.controller;

import com.example.webfluxjwt.models.reqrespbodies.ReqLogin;
import com.example.webfluxjwt.models.reqrespmodel.ReqRespModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @GetMapping("/auth")
    public Mono<ResponseEntity<ReqRespModel<String>>> auth() {
        return Mono.just(ResponseEntity.ok(new ReqRespModel<>("Welcome to the private club", ""
        )));
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<ReqRespModel<String>>>  login(@RequestBody ReqLogin user) {
        return Mono.just(ResponseEntity.ok(
                new ReqRespModel<>("Login page plase login", "")
        ));
    }
}
