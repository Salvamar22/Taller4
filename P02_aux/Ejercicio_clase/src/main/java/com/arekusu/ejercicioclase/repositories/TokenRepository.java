package com.arekusu.ejercicioclase.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.arekusu.ejercicioclase.models.entities.Token;
import com.arekusu.ejercicioclase.models.entities.User;

public interface TokenRepository extends ListCrudRepository<Token, UUID>{

	List<Token> findByUserAndActive(User user, Boolean active);
}
