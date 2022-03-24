package com.eaa.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaa.ecommerce.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
