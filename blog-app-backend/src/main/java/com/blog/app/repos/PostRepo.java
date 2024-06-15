package com.blog.app.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory( Category category);
	
//	List<Post> findByTitleContaining(String title);// this works. but to be safe, writing our own method.
	
	@Query("select p from Post p where p.title like :key")
	List<Post> findByTitleContaining(@Param(value = "key") String title);// this works. but to be safe, writing our own method.
}
