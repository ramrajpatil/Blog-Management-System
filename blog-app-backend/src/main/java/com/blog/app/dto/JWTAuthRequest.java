package com.blog.app.dto;

import lombok.Data;

@Data
public class JWTAuthRequest {

	private String username;
	
	private String password;
}
