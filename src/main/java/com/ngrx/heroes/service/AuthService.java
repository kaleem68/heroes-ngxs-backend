package com.ngrx.heroes.service;


import com.ngrx.heroes.entity.User;
import com.ngrx.heroes.model.AuthenticationResponse;
import com.ngrx.heroes.model.LoginRequest;
import com.ngrx.heroes.repository.UserRepository;
import com.ngrx.heroes.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;



    public AuthenticationResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());

        Authentication authenticate =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContext context = SecurityContextHolder.getContext();

        context.setAuthentication(authenticate);

        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());

    }



    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                        getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User name not found - " + principal.getUsername()));
    }


}

