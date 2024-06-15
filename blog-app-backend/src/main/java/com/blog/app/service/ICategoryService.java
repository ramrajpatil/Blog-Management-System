package com.blog.app.service;

import java.util.List;

import com.blog.app.dto.CategoryDTO;

public interface ICategoryService {

	// Create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	// Update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
	
	// Delete
	void deleteCategory(Integer categoryId);
	
	// Get
	CategoryDTO getCategory(Integer categoryId);

	// Get all
	List<CategoryDTO> getAllCategories();
	
	
}
