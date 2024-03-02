package org.example.jwtloginexample.controllers;

import lombok.RequiredArgsConstructor;
import org.example.jwtloginexample.dtos.UserDTO;
import org.example.jwtloginexample.dtos.UserUpdateRequest;
import org.example.jwtloginexample.dtos.UserRegistrationRequest;
import org.example.jwtloginexample.entities.User;
import org.example.jwtloginexample.services.JwtService;
import org.example.jwtloginexample.services.UserService;
import org.example.jwtloginexample.utility.UserDTOMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public List<UserDTO> findAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable int id) {
        return userService.findUserById(id);

    }

    @GetMapping("/{email}")
    public UserDTO findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        var user = userService.addUser(request);
        String token = jwtService.generateToken(user.username(), user.role().name());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(user);
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable int id,
                           @RequestBody UserUpdateRequest updateRequest) {
        userService.updateUser(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
