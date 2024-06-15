package com.blog.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.app.dto.PostDTO;
import com.blog.app.dto.PostResponseDTO;
import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repos.CategoryRepo;
import com.blog.app.repos.PostRepo;
import com.blog.app.repos.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostServiceImpl implements IPostService {

	@Autowired
	private PostRepo pRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo uRepo;

	@Autowired
	private CategoryRepo cRepo;

	@Override
	public PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId) {
		User user = this.uRepo.findById(userId).orElseThrow(() -> 
				new ResourceNotFoundException("User", "id ", userId));

		Category cat = this.cRepo.findById(categoryId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Category ", " id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		// Title and content already set using model mapper.
		post.setImageName("default.png");
		post.setAddDate(LocalDate.now());
		post.setUser(user);
		post.setCategory(cat);

		Post savedpost = pRepo.save(post);

		return this.modelMapper.map(savedpost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, Integer postId) {
		Post post = this.pRepo.findById(postId).orElseThrow(() -> 
		new ResourceNotFoundException("Post", "id", postId));

		Category cat = this.cRepo.findById(postDto.getCategory().getId())
				.orElseThrow(() -> 
				new ResourceNotFoundException("Category ", " id", postDto.getCategory().getId()));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setCategory(cat);
		Post updatedPost = this.pRepo.save(post);

		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.pRepo.findById(postId).orElseThrow(() -> 
		new ResourceNotFoundException("Post", "id", postId));

		pRepo.delete(post);

	}

	@Override
	public PostDTO getPostById(Integer postId) {
		Post post = this.pRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// Paging and sorting
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = pRepo.findAll(pageable);

		List<Post> posts = pagePost.getContent();
//		List<Post> posts = pRepo.findAll();

		List<PostDTO> postDtos = posts.stream().map(p -> this.modelMapper.map(p, PostDTO.class))
				.collect(Collectors.toList());

		PostResponseDTO resp = new PostResponseDTO();
		resp.setContent(postDtos);
		resp.setPageNumber(pagePost.getNumber());
		resp.setPageSize(pagePost.getSize());
		resp.setTotalElements(pagePost.getTotalElements());
		resp.setTotalPages(pagePost.getTotalPages());
		resp.setLastPage(pagePost.isLast());

		return resp;
	}

	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {

		Category cat = this.cRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", " id", categoryId));

		List<Post> posts = this.pRepo.findByCategory(cat);

		List<PostDTO> postDtoList = posts.stream().map(p -> this.modelMapper.map(p, PostDTO.class))
				.collect(Collectors.toList());

		return postDtoList;
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		User user = this.uRepo.findById(userId).orElseThrow(() -> 
		new ResourceNotFoundException("User", "id ", userId));

		List<Post> posts = this.pRepo.findByUser(user);

		List<PostDTO> postDtoList = posts.stream().map(p -> this.modelMapper.map(p, PostDTO.class))
				.collect(Collectors.toList());

		return postDtoList;
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
//		List<Post> posts = this.pRepo.findByTitleContaining(keyword);
		List<Post> posts = this.pRepo.findByTitleContaining("%" + keyword + "%");

		List<PostDTO> postDtos = posts.stream().map(p -> this.modelMapper.map(p, PostDTO.class))
				.collect(Collectors.toList());

		return postDtos;
	}

}
