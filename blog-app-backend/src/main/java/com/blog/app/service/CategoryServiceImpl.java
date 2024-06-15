package com.blog.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.dto.CategoryDTO;
import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repos.CategoryRepo;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryRepo cRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category cat = this.modelMapper.map(categoryDTO, Category.class);
		
		Category savedCat = this.cRepo.save(cat);
		
		
		return this.modelMapper.map(savedCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		Category cat = this.cRepo.findById(categoryId)
				.orElseThrow(() ->
				new ResourceNotFoundException("Category ", " id" , categoryId));
		
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescription(categoryDTO.getCategoryDescription());
		
		Category updateCat = this.cRepo.save(cat);
		
		return this.modelMapper.map(updateCat, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.cRepo.findById(categoryId)
				.orElseThrow(() ->
				new ResourceNotFoundException("Category ", " id" , categoryId));
		
		this.cRepo.delete(cat);

	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category cat = this.cRepo.findById(categoryId)
				.orElseThrow(() ->
				new ResourceNotFoundException("Category ", " id" , categoryId));
		
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> catList = this.cRepo.findAll();
		
		List<CategoryDTO> catDtoList = catList.stream().map(c -> this.modelMapper.map(c, CategoryDTO.class))
		.collect(Collectors.toList());
		
		return catDtoList;
	}

}
