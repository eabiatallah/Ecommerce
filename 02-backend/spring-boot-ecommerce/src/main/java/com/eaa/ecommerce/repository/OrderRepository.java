package com.eaa.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaa.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
