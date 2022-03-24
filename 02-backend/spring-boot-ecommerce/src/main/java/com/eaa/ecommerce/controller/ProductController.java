package com.eaa.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eaa.ecommerce.exception.NotFoundException;
import com.eaa.ecommerce.model.Product;
import com.eaa.ecommerce.repository.ProductRepository;
import com.eaa.ecommerce.response.ProductPaginationResponse;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products")
	public List<Product> getProducts() {
		List<Product> productresponse = productRepository.findAll();
		return productresponse;
	}

	@GetMapping("/product/{id}")
	public Optional<Product> getProduct(@PathVariable("id") Long id) {
		Optional<Product> productresponse = productRepository.findById(id);
		if (!productresponse.isPresent()) {
			throw new NotFoundException("id - " + id);
		}
		return productresponse;
	}

	@GetMapping("/category")
	public List<Product> getProductsCategory(@RequestParam(value = "id") Long id) {
		List<Product> response = productRepository.findByCategoryId(id);
		return response;
	}

	@GetMapping("/product/keyword")
	public List<Product> getProductsByKeyword(@RequestParam(value = "name") String name) {
		List<Product> productsByName = productRepository.findByNameContaining(name);
		return productsByName;
	}

	@GetMapping("/getProductsByKey/{keyword}/{offset}/{pageSize}")
	public ProductPaginationResponse getProductsAndPaginationByKeyword(@PathVariable String keyword,
			@PathVariable int offset, @PathVariable int pageSize) {
		ProductPaginationResponse prResponse = new ProductPaginationResponse();
		Page<Product> productsByKeyword = productRepository.findByNameContaining(keyword,
				PageRequest.of(offset, pageSize));

		prResponse.setContent(productsByKeyword.getContent());
		prResponse.setNumber(productsByKeyword.getNumber());
		prResponse.setSize(productsByKeyword.getSize());
		prResponse.setTotalElements(productsByKeyword.getTotalElements());
		prResponse.setTotalPages(productsByKeyword.getTotalPages());
		return prResponse;
	}

	@GetMapping("/products/{categoryId}/{offset}/{pageSize}")
	public ProductPaginationResponse getProductsCategoryPagination(@PathVariable Long categoryId,
			@PathVariable int offset, @PathVariable int pageSize) {
		ProductPaginationResponse prResponse = new ProductPaginationResponse();
		Page<Product> productWithPagination =  productRepository.findByCategoryId(categoryId,
				PageRequest.of(offset, pageSize));

		prResponse.setContent(productWithPagination.getContent());
		prResponse.setNumber(productWithPagination.getNumber());
		prResponse.setSize(productWithPagination.getSize());
		prResponse.setTotalElements(productWithPagination.getTotalElements());
		prResponse.setTotalPages(productWithPagination.getTotalPages());
		return prResponse;
	}

}
