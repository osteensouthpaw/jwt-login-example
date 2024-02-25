package org.example.jwtloginexample.services;

import lombok.RequiredArgsConstructor;
import org.example.jwtloginexample.dtos.AuthenticationRequest;
import org.example.jwtloginexample.dtos.AuthenticationResponse;
import org.example.jwtloginexample.dtos.RegistrationRequest;
import org.example.jwtloginexample.entities.User;
import org.example.jwtloginexample.repositories.UserRepository;
import org.example.jwtloginexample.roles.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest request) {
        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .age(request.age())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        user = userRepository.save(user);
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );
        authenticationManager.authenticate(auth);
        var user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("not found"));

        return getAuthenticationResponse(user);
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
