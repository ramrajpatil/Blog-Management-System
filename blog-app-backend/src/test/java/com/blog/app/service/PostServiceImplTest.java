package com.blog.app.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.app.dto.PostDTO;
import com.blog.app.repos.PostRepo;

@SpringBootTest
class PostServiceImplTest {

	@Autowired
	private IPostService pService;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCreatePost() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdatePost() {
		fail("Not yet implemented");
	}

	@Test
	void testDeletePost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPostById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllPosts() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPostByCategory() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPostByUser() {
		List<PostDTO> list = pService.getPostByUser(1);
		
		list.forEach(p -> System.out.println(p));
	}

	@Test
	void testSearchPost() {
		fail("Not yet implemented");
	}

}
