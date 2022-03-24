package com.eaa.ecommerce.repository;

 

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaa.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByCategoryId (Long id);
	
	List<Product> findByNameContaining(String name);
	
	Page<Product> findByCategoryId (Long id, Pageable pageable);
	
	Page<Product> findByNameContaining(String name, Pageable pageable);

}
