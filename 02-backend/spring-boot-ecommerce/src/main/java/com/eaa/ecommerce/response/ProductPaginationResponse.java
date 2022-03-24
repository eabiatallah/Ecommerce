package com.eaa.ecommerce.response;

import java.io.Serializable;
import java.util.List;

import com.eaa.ecommerce.model.Product;

import lombok.Data;

@Data
public class ProductPaginationResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Product> content;
	private int size;
	private long totalElements;
	private int totalPages;
	private int number;

}
