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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.dto.CategoryDTO;
import com.blog.app.dto.ResponseDTO;
import com.blog.app.service.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin("*")
public class CategoryRestController {

	@Autowired
	private ICategoryService cService;

	// Create
	@PostMapping("/")
	public ResponseEntity<?> createNewCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO category = this.cService.createCategory(categoryDTO);
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	// Update
	@PutMapping("/{catId}")
	public ResponseEntity<?> updateCategoryDetails(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer catId) {
		CategoryDTO updatedCategory = this.cService.updateCategory(categoryDTO, catId);
		
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	}
	
	// Delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<?> deleteCategoryDetails(@PathVariable Integer catId) {
		this.cService.deleteCategory(catId);
		
		return new ResponseEntity<>(new ResponseDTO("Category deleted successfully !!", true), HttpStatus.OK);
	}
	
	// Get
	@GetMapping("/{catId}")
	public ResponseEntity<?> getCategoryDetails(@PathVariable Integer catId) {
		CategoryDTO categoryDTO = this.cService.getCategory(catId);
		
		return ResponseEntity.ok(categoryDTO);
	}
	
	
	// Get All
	@GetMapping("/")
	public ResponseEntity<?> getAllCategories() {
		List<CategoryDTO> allCategories = this.cService.getAllCategories();
		
		return ResponseEntity.ok(allCategories);
	}
}
