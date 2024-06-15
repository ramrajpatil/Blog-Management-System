package com.blog.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.dto.CommentDTO;
import com.blog.app.entities.Comment;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repos.CommentRepo;
import com.blog.app.repos.PostRepo;
import com.blog.app.repos.UserRepo;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	private CommentRepo cRepo;
	
	@Autowired
	private PostRepo pRepo;
	
	@Autowired
	private UserRepo uRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDto, Integer postId, Integer userId) {
		Post post = this.pRepo.findById(postId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Post", "id", postId));

		User user = this.uRepo.findById(userId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("User", "id ", userId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.cRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void delete(Integer commentId) {
		Comment com = this.cRepo.findById(commentId)
				.orElseThrow(() ->  
				new ResourceNotFoundException("Comment", "id", commentId));

		this.cRepo.delete(com);
	}

	@Override
	public CommentDTO getSingleComment(Integer commentId) {
		Comment com = this.cRepo.findById(commentId)
				.orElseThrow(() ->  
				new ResourceNotFoundException("Comment", "id", commentId));

		return this.modelMapper.map(com, CommentDTO.class);
	}

	@Override
	public List<CommentDTO> getAllComments() {
		List<Comment> comments = this.cRepo.findAll();
		
		List<CommentDTO> commentDtos = comments.stream().map(c -> this.modelMapper.map(c, CommentDTO.class))
		.collect(Collectors.toList());
		
		
		return commentDtos;
	}

	@Override
	public CommentDTO updateComment(CommentDTO commentDto, Integer commentId) {
		
		
		
		return null;
	}

}
