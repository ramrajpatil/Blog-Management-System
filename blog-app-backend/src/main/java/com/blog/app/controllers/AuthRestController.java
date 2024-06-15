package com.blog.app.controllers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.dto.JWTAuthRequest;
import com.blog.app.dto.JWTAuthResponse;
import com.blog.app.dto.UserDTO;
import com.blog.app.entities.User;
import com.blog.app.security.JWTTokenHelper;
import com.blog.app.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthRestController {

	@Autowired
	private JWTTokenHelper helper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserService uService;
	
	@Autowired
	private ModelMapper mapper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/login")
	public ResponseEntity<?> createToken(@RequestBody JWTAuthRequest request) {
		logger.info("In create token / login");

		logger.info("Username: " + request.getUsername());
		logger.info("Password: " + request.getPassword());

		this.autheticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.helper.generateToken(userDetails.getUsername());

		logger.info("Before JWTAuthResponse: "+token);
		
		

		JWTAuthResponse response = new JWTAuthResponse();
		response.setToken(token);
		
		response.setUser(this.mapper.map((User) userDetails, UserDTO.class));
		logger.info("In JWTAuthResponse: "+response.getToken());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void autheticate(String username, String passowrd) {
		logger.info("Username from authenticate() : " + username + " and passowrd : " + passowrd);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				passowrd);

		try {
			this.authenticationManager.authenticate(authenticationToken);
			logger.info("After authentication: "+authenticationToken.getPrincipal().toString());
			logger.info("After authentication: "+authenticationToken.getCredentials().toString());
		} catch (BadCredentialsException e) {
			System.out.println(
					"In error of authenticate() of " + getClass().getName() + " error message : " + e.getMessage());
			throw new BadCredentialsException("Invalid username or passowrd");
		}
	}

	// Register new user API
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDto) {

		UserDTO registeredUserDto = this.uService.registerNewUser(userDto);

		return new ResponseEntity<>(registeredUserDto, HttpStatus.CREATED);
	}

}
