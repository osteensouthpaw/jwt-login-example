package org.example.jwtloginexample.dtos;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        int age,
        String email
) {
}
