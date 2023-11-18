package com.example.webfluxjwt.models.reqrespbodies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ReqLogin {
    private String email;
    private String password;
}
