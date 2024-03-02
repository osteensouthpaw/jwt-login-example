package org.example.jwtloginexample.utility;

import org.example.jwtloginexample.dtos.UserDTO;
import org.example.jwtloginexample.entities.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getRole(),
                user.getUsername());
    }
}
