package com.blog.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.dto.ResponseDTO;
import com.blog.app.dto.UserDTO;
import com.blog.app.service.IUserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserRestController {

	
	private Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	private IUserService uService;
	
	// GET - getting all users
	@GetMapping("/")
	public ResponseEntity<?> getAllUsers(){
		this.logger.info("In get all Users request.");
		return ResponseEntity.ok(this.uService.getAllUsers());
	}
	
	// GET - get single user
	@GetMapping("/{userId}")
	public ResponseEntity<?> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.uService.getUserByID(userId));
		
	}
	
	// POST - create user
	@PostMapping("/")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDto){
		UserDTO createdUserDto = this.uService.createUser(userDto);
		
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	// PUT- update user
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUserDetails(@PathVariable Integer userId, @Valid @RequestBody UserDTO userDto) {
		
		return new ResponseEntity<>(this.uService.updateUser(userDto, userId), HttpStatus.OK);
	}
	
	// ADMIN
	// DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUserDetails(@PathVariable Integer userId){
		this.uService.deleteUser(userId);
//		return new ResponseEntity<>(Map.of("message","User deleted successfully"), HttpStatus.OK);
		return new ResponseEntity<>(new ResponseDTO("User deleted successfully", true), HttpStatus.OK);
	}
	
}
