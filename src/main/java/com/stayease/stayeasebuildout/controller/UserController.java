package com.stayease.stayeasebuildout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stayease.stayeasebuildout.dtos.AuthRequest;
import com.stayease.stayeasebuildout.dtos.TokenResponse;
import com.stayease.stayeasebuildout.models.Users;
import com.stayease.stayeasebuildout.service.UserService;
import com.stayease.stayeasebuildout.utils.JWTUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users user) {
        if(!this.userService.findUser(user.getEmail())) {
            if(user.getRole()==null) {
                user.setRole("Customer");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userService.registerUser(user);
            String token = jwtUtils.generateToken(user.getEmail());
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        }
        catch(Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String token = jwtUtils.generateToken(authRequest.getEmail());
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
