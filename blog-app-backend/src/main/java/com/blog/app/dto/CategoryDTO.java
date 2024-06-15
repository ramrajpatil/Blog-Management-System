package com.blog.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

	
	private Integer id;
	
	@NotEmpty(message = "Category title cannot be empty")
	@Size(min = 4, message ="Category title must be at least 4 characters." )
	private String categoryTitle;
	
	@NotEmpty(message = "Category description cannot be empty")
	@Size(min = 10, message ="Category title must be at least 4 characters." )
	private String categoryDescription;
}
