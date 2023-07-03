package com.arekusu.ejercicioclase.controllers;

import com.arekusu.ejercicioclase.models.dtos.MessageDTO;
import com.arekusu.ejercicioclase.models.dtos.TokenDTO;
import com.arekusu.ejercicioclase.models.dtos.UserDTO;
import com.arekusu.ejercicioclase.models.dtos.UserLoginDTO;
import com.arekusu.ejercicioclase.models.entities.Playlist;
import com.arekusu.ejercicioclase.models.entities.Token;
import com.arekusu.ejercicioclase.models.entities.User;
import com.arekusu.ejercicioclase.repositories.UserRepository;
import com.arekusu.ejercicioclase.services.PlaylistService;
import com.arekusu.ejercicioclase.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.arekusu.ejercicioclase.utils.JWTTools;
import com.arekusu.ejercicioclase.utils.RequestErrorHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    
    @Autowired
    private PlaylistService playlistService;
    
    @Autowired
    private JWTTools jwtTools;
    
    @Autowired
    private UserRepository userRepository;
 

    @Autowired
    public void PlaylistController(PlaylistService playlistService, UserService userService, JWTTools jwtTools, UserRepository userRepository) {
        this.playlistService = playlistService;
        this.userService = userService;
        this.jwtTools = jwtTools;
        this.userRepository = userRepository;
    }
    
    private RequestErrorHandler errorHandler;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username, username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO, BindingResult validations) {
    	
    	if(validations.hasErrors()) {
    		return new ResponseEntity<> (
        			errorHandler.mapErrors(validations.getFieldErrors()),HttpStatus.BAD_REQUEST);
    	}    	
        try {
            userService.createUser(userDTO);
            return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    
}
    
    @GetMapping("/user/playlist")
    public ResponseEntity<?> getPlaylistByUser(HttpServletRequest request, @RequestParam("title") String title){
    	String tokenHeader = request.getHeader("Authorization");
    	String token = tokenHeader.substring(7);
    	String username = jwtTools.getUsernameFrom(token);
		User user = userRepository.findByUsername(username);
    	if(user == null) {
    		return new ResponseEntity<>(
    				new MessageDTO("User not found"),HttpStatus.NOT_FOUND);
    	}
    	List<Playlist> playlists;
        if (title != null && !title.isEmpty()) {
            playlists = playlistService.findPlaylistsByUserAndTitle(user, title);
        } else {
            playlists = playlistService.findPlaylistByUser(user);
        }
        return ResponseEntity.ok(playlists);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute @Valid UserLoginDTO info, BindingResult validations){
    	if(validations.hasErrors()) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.findOneByIdentifier(info.getIdentifier(), info.getIdentifier());
		
		if(user == null) {
			return new ResponseEntity<>("Usuario inexistente", HttpStatus.NOT_FOUND);
		}
		
		if(!userService.comparePassword(info.getPassword(), user.getPassword())) {
			return new ResponseEntity<>("Error de credenciales", HttpStatus.UNAUTHORIZED);
		}
		try {
			Token token = userService.registerToken(user);
			return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
 }  

