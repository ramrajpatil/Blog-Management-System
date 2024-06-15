package com.blog.app.service;

import java.util.List;

import com.blog.app.dto.UserDTO;


public interface IUserService {

	
	UserDTO registerNewUser(UserDTO user);
	
	
	UserDTO createUser (UserDTO user);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserByID(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
	
}
