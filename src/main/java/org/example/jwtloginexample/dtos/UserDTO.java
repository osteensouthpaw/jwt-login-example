package org.example.jwtloginexample.dtos;

import org.example.jwtloginexample.roles.Role;

public record UserDTO(
        String firstName,
        String lastName,
        int age,
        String email,
        Role role,
        String username
) {
}
