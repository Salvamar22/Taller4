package com.arekusu.ejercicioclase.repositories;

import com.arekusu.ejercicioclase.models.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByUsernameOrEmail(String username, String email);
    
}
