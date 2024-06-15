package com.blog.app.service;

import java.util.List;

import com.blog.app.dto.PostDTO;
import com.blog.app.dto.PostResponseDTO;

public interface IPostService {

	// Create
	PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId);
	
	// Update
	PostDTO updatePost(PostDTO postDto, Integer postId);
	
	// Delete
	void deletePost(Integer postId);
	
	// Get
	PostDTO getPostById(Integer postId);
	
	// Get all
//	List<PostDTO> getAllPosts(Integer pageNumber, Integer pageSize);
	PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	// get all posts by category
	List<PostDTO> getPostByCategory(Integer categoryId);
	
	// get all posts by user
	List<PostDTO> getPostByUser(Integer userId);
	
	// Search posts by keyword.
	List<PostDTO> searchPost(String keyword);
}
