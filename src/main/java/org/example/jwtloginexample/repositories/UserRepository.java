package org.example.jwtloginexample.repositories;

import jakarta.persistence.NamedNativeQuery;
import org.example.jwtloginexample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    boolean existsUserByEmail(String email);
}
