package com.blog.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthResponse {

	private String token;
	
	private UserDTO user;
	
}
