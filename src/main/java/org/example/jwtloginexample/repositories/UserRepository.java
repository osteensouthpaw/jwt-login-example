package org.example.jwtloginexample.repositories;

import org.example.jwtloginexample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
}
