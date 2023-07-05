package com.arekusu.ejercicioclase.security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.modelmapper.ModelMapper;


import com.arekusu.ejercicioclase.models.entities.User;
import com.arekusu.ejercicioclase.services.UserService;
import com.arekusu.ejercicioclase.utils.JWTTokenFIlter;


import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTTokenFIlter filter;
	
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	
	@Bean
	AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder managerBuilder 
	    	= http.getSharedObject(AuthenticationManagerBuilder.class);
	    
	    managerBuilder
	    	.userDetailsService(identifier -> {
	    		User user = userService.findOneByIdentifier(identifier, identifier);
	    		
	    		if(user == null)
	    			throw new UsernameNotFoundException("User: " + identifier + ", not found!");
	    		
	    		return user;
	    	})
	    	.passwordEncoder(passwordEncoder);
	    
	    return managerBuilder.build();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.cors(Customizer.withDefaults());
		
		http
			.httpBasic().and().csrf().disable();
		
		 //Route filter
	    http.authorizeHttpRequests(auth -> 
	    	auth
	    		.requestMatchers("/api/login").permitAll()
	    		.requestMatchers("/api/auth/signup").permitAll()
	    		.anyRequest().authenticated()
	    );
	    
	    //Statelessness
	    http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    
	    //UnAunthorized handler
	    http.exceptionHandling(handling -> handling.authenticationEntryPoint((req, res, ex) -> {
	        res.sendError(
	        		HttpServletResponse.SC_UNAUTHORIZED,
	        		"Auth fail!"
	        	);
	    }));
	    
	    //JWT filter
	    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}