package org.example.jwtloginexample.dtos;

public record RegistrationRequest(
        String firstName,
        String lastName,
        int age,
        String email,
        String password
) {
}
