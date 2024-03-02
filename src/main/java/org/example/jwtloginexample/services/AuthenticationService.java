package org.example.jwtloginexample.services;

import lombok.RequiredArgsConstructor;
import org.example.jwtloginexample.dtos.AuthenticationRequest;
import org.example.jwtloginexample.dtos.AuthenticationResponse;
import org.example.jwtloginexample.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );
        Authentication authentication = authenticationManager.authenticate(auth);
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        return new AuthenticationResponse(token);
    }
}
