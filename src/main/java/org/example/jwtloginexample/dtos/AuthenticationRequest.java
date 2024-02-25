package org.example.jwtloginexample.dtos;

public record AuthenticationRequest(
        String email,
        String password
) {
}
