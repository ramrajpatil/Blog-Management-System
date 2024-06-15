package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.dto.CommentDTO;
import com.blog.app.dto.ResponseDTO;
import com.blog.app.service.ICommentService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CommentRestController {

	@Autowired
	private ICommentService cService;
	
	// create new comment.
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<?> addNewComment(@RequestBody CommentDTO commentDto,
			@PathVariable Integer postId,
			@PathVariable Integer userId
			 ){
		CommentDTO dto = this.cService.createComment(commentDto, postId, userId);
		
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	// delete comment.
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
		
		this.cService.delete(commentId);
		
		return ResponseEntity.ok(new ResponseDTO("Comment with id : "+commentId+" deleted successfully !!!", true));
	}
	
	// get all comments
	@GetMapping("/comments/")
	public ResponseEntity<?> getAllComments(){
		List<CommentDTO> comments = this.cService.getAllComments();
		
		return ResponseEntity.ok(comments);
	}
	
	// Get single comment
	@GetMapping("/comments/{commentId}")
	public ResponseEntity<?> getCommentDetails(@PathVariable Integer commentId) {
		
		CommentDTO commentDto = this.cService.getSingleComment(commentId);
		
		return ResponseEntity.ok(commentDto);
	}
	
	
}
