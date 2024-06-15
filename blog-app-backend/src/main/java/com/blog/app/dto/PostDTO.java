package com.blog.app.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private Integer postId;
	
	@NotEmpty(message = "Post title cannot be empty")
	@Size(min =4, message= "Post title must contain at least 4 characters")
	private String title;
	
	@NotEmpty(message = "Post content cannot be empty")
	@Size(min =15, message= "Post content must contain at least 15 characters")
	private String content;
	
	private String imageName;
	
	private LocalDate addDate;
	
	private UserDTO user;
	
	private CategoryDTO category;
	
	// As we we'll be always fetching posts along with the comments
	private Set<CommentDTO> comments = new HashSet<>();
}
