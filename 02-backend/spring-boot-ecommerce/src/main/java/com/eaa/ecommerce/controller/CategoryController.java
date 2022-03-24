package com.eaa.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaa.ecommerce.model.ProductCategory;
import com.eaa.ecommerce.repository.ProductCategoryRepository;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class CategoryController {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@GetMapping("/categories")
	public List<ProductCategory> getCategories() {
		List<ProductCategory> categoryResponse = productCategoryRepository.findAll();
		return categoryResponse;
	}

}
