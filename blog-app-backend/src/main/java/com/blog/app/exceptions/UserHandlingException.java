package com.blog.app.exceptions;

public class UserHandlingException extends RuntimeException {

	private String resourceName;
	
	private String fieldName;
	
	private String fieldValue;
	
	public UserHandlingException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s",resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
