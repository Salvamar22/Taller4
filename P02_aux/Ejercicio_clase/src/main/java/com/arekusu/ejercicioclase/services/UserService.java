package com.arekusu.ejercicioclase.services;

import com.arekusu.ejercicioclase.models.dtos.UserDTO;
import com.arekusu.ejercicioclase.models.entities.Token;
import com.arekusu.ejercicioclase.models.entities.User;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    List<User> getAllUsers();
    User getUserByUsername(String username, String email);
    User createUser(UserDTO userDTO);
    User findOneByIdentifier(String username, String email);
    Boolean comparePassword(String toCompare, String current);
    Token registerToken(User user) throws Exception;
	Boolean isTokenValid(User user, String token);
	void cleanTokens(User user) throws Exception;
	User findUserAuthenticated();
}
