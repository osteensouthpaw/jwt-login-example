package org.example.jwtloginexample.services;

import lombok.RequiredArgsConstructor;
import org.example.jwtloginexample.dtos.UserDTO;
import org.example.jwtloginexample.dtos.UserUpdateRequest;
import org.example.jwtloginexample.dtos.UserRegistrationRequest;
import org.example.jwtloginexample.entities.User;
import org.example.jwtloginexample.repositories.UserRepository;
import org.example.jwtloginexample.roles.Role;
import org.example.jwtloginexample.utility.UserDTOMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(int id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


    public UserDTO findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(userDTOMapper)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


    public boolean userExistsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }


    public UserDTO addUser(UserRegistrationRequest request) {
        if (userExistsByEmail(request.email()))
            throw new RuntimeException("user already exists");

        var user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .age(request.age())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        user = userRepository.save(user);
        return userDTOMapper.apply(user);
    }


    public void updateUser(int id, UserUpdateRequest updateRequest) {
        //check if the customer exists
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        boolean changes = false;

        //update firstname
        if (updateRequest.firstName() != null && !updateRequest.firstName().equals(user.getFirstName())) {
            user.setFirstName(updateRequest.firstName());
            changes = true;
        }

        //update lastname
        if (updateRequest.lastName() != null && !updateRequest.lastName().equals(user.getLastName())) {
            user.setLastName(updateRequest.lastName());
            changes = true;
        }

        //update email
        if (updateRequest.email() != null && !updateRequest.email().equals(user.getEmail())) {
            if (userExistsByEmail(updateRequest.email()))
                throw new RuntimeException("email already exist");

            user.setEmail(updateRequest.email());
            changes = true;
        }

        //update age
        if (updateRequest.age() != 0 && updateRequest.age() != (user.getAge())) {
            user.setAge(updateRequest.age());
            changes = true;
        }

        if (!changes) throw new RuntimeException("No changes detected");

        userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not found")));
    }
}
