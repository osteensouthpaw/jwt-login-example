package org.example.jwtloginexample.controllers;

import lombok.RequiredArgsConstructor;
import org.example.jwtloginexample.dtos.AuthenticationRequest;
import org.example.jwtloginexample.dtos.AuthenticationResponse;
import org.example.jwtloginexample.dtos.UserRegistrationRequest;
import org.example.jwtloginexample.services.AuthenticationService;
import org.example.jwtloginexample.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authService;


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
