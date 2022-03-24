package com.eaa.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaa.ecommerce.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	
	List<State> findByCountryCode(String code);

}
