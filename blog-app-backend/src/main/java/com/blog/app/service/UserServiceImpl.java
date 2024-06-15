package com.blog.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.config.AppConstants;
import com.blog.app.dto.UserDTO;
import com.blog.app.entities.Role;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repos.RoleRepo;
import com.blog.app.repos.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepo uRepo; // jdk.proxy2.$Proxy99 => Impl class of UserRepo i/f.
	// All impl classes are called as proxy classes.
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = this.dtoToUser(userDto);
		
		User savedUser = uRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		// Get
		User user = this.uRepo.findById(userId)
				.orElseThrow(()
						-> new ResourceNotFoundException("User","id ",userId));
		// Set
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		// Update
		User updatedUser = this.uRepo.save(user);
		
		//Convert and return
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDTO getUserByID(Integer userId) {
		User user = this.uRepo.findById(userId)
				.orElseThrow(()
						-> new ResourceNotFoundException("User","id ",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		
		List<User> users = uRepo.findAll();
		
		List<UserDTO> userDtos = users.stream()
				.map(user -> this.userToDto(user))
				.collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.uRepo.findById(userId)
				.orElseThrow(()
						-> new ResourceNotFoundException("User","id ",userId));
		this.uRepo.delete(user);
		
	}

	private User dtoToUser(UserDTO userDto) {
		User newUser = this.modelMapper
				.map(userDto, User.class);
//		newUser.setId(user.getId());
//		newUser.setName(user.getName());
//		newUser.setEmail(user.getEmail());
//		newUser.setPassword(user.getPassword());
//		newUser.setAbout(user.getAbout());
		
		
		return newUser;
	}
	
	public UserDTO userToDto(User user) {
		UserDTO userDto = this.modelMapper
				.map(user, UserDTO.class);
		return userDto;
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		// Encoding the password before storing it in DB.
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// Assigning roles: Assuming new user will be assigned a normal role.
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User registeredUser = this.uRepo.save(user);
		
		return this.modelMapper.map(registeredUser, UserDTO.class);
	}
	
}
