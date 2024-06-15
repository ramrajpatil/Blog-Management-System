package com.blog.app.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "Name cannot be empty or must be min of 4 characters.")
	private String name;
	
	@NotEmpty(message = "Email address cannot be empty or is not valid.")
	@Email
	private String email;
	
	
	@NotEmpty
	@Size(min = 3, max = 20, message = "Password cannot be empty or must be between 3 to 20 characters.")
	private String password;
	
	@NotNull
	@Size(min=10, max=500, message = "Must not be empty and required minimum 10 characters.")
	private String about;
	
	private Set<RoleDTO> roles = new HashSet<>();
	
	
	@JsonIgnore// We want to ignore this while serialization
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty// We don't want to ignore this while de-serialization.
	public void setPassword(String password) {
		this.password=password;
	}
}
