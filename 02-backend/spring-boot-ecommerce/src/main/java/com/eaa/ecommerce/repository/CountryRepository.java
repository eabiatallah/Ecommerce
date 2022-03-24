package com.eaa.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eaa.ecommerce.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{

}
