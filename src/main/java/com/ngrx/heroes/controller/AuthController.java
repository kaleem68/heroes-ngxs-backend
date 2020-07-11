package com.ngrx.heroes.controller;


import com.ngrx.heroes.model.AuthenticationResponse;
import com.ngrx.heroes.model.LoginRequest;
import com.ngrx.heroes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}

