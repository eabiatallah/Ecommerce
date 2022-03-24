package com.eaa.ecommerce.dto;

import java.util.Set;

import com.eaa.ecommerce.model.Address;
import com.eaa.ecommerce.model.Customer;
import com.eaa.ecommerce.model.Order;
import com.eaa.ecommerce.model.OrderItem;

import lombok.Data;

@Data
public class PurchaseRequest {
	
	private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;


}
