package org.example.jwtloginexample.dtos;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        int age,
        String email,
        String password,
        String role
) {
}
