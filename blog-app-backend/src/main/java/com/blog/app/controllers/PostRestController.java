package com.blog.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.config.AppConstants;
import com.blog.app.dto.PostDTO;
import com.blog.app.dto.PostResponseDTO;
import com.blog.app.dto.ResponseDTO;
import com.blog.app.service.IFileService;
import com.blog.app.service.IPostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PostRestController {

	@Autowired
	private IPostService pService;

	@Autowired
	private IFileService fileService;

	@Value("${project.image}") // From application.properties file.
	private String path;

	// Create
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<?> addNewPost(@Valid @RequestBody PostDTO postDto, @PathVariable Integer userId,
			@PathVariable Integer catId) {

		PostDTO savedPost = pService.createPost(postDto, userId, catId);

		return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
	}

	// Get by User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<?> getPostsByUser(@PathVariable Integer userId) {

		List<PostDTO> list = this.pService.getPostByUser(userId);

		return ResponseEntity.ok(list);

	}

	// Get by Category
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<?> getPostsByCategory(@PathVariable Integer catId) {

		List<PostDTO> list = this.pService.getPostByCategory(catId);

		return ResponseEntity.ok(list);

	}

	// Get all posts
	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts( // Using constant from config package class.
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostResponseDTO allPosts = this.pService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

		return ResponseEntity.ok(allPosts);
	}

	// Get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<?> getPostDetails(@PathVariable Integer postId) {
		PostDTO dto = this.pService.getPostById(postId);

		return ResponseEntity.ok(dto);
	}

	// Delete post by id
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
		this.pService.deletePost(postId);

		return ResponseEntity.ok(new ResponseDTO("Post deleted successfully !!!", true));
	}

	// Update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(@Valid @RequestBody PostDTO postDto, @PathVariable Integer postId) {
		PostDTO updatePost = this.pService.updatePost(postDto, postId);

		return ResponseEntity.ok(updatePost);
	}

	// Search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<?> searchPostByTitle(@PathVariable String keyword) {

		List<PostDTO> result = this.pService.searchPost(keyword);

		return ResponseEntity.ok(result);
	}

	// Post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId)
			throws IOException {

		PostDTO postDto = this.pService.getPostById(postId);

		String imageName = this.fileService.uploadImage(path, image);

		postDto.setImageName(imageName);
		PostDTO updatePost = this.pService.updatePost(postDto, postId);

		return ResponseEntity.ok(updatePost);
	}

	// Serve Image
	@GetMapping(value = "/post/image/{imageName}", 
			produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName, 
			HttpServletResponse response)
			throws IOException {

		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());
	}

}
