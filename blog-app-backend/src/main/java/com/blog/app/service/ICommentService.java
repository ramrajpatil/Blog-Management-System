package com.blog.app.service;

import java.util.List;

import com.blog.app.dto.CommentDTO;

public interface ICommentService {

	
	// Create
	CommentDTO createComment(CommentDTO commentDto, Integer postId, Integer userId);
	
	// Delete
	void delete(Integer commentId);
	
	// Get single comment
	CommentDTO getSingleComment(Integer commentId);
	
	// Get All Comments
	List<CommentDTO> getAllComments();
	
	// Update
	CommentDTO updateComment(CommentDTO commentDto, Integer commentId);
	
	// get by User
	//To be written later if time permits.
	
}
