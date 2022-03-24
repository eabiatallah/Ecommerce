package com.eaa.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.eaa.ecommerce.exception.NotFoundException;
import com.eaa.ecommerce.model.Country;
import com.eaa.ecommerce.repository.CountryRepository;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
public class CountryController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@GetMapping("/countries")
	public List<Country> getCountries() {
		List<Country> countryResponse = countryRepository.findAll();
		return countryResponse;
	}
	
	@GetMapping("/country/{id}")
	public Optional<Country> getCountry(@PathVariable("id") int id) {
		Optional<Country> countryResponse = countryRepository.findById(id);
		if (!countryResponse.isPresent()) {
			throw new NotFoundException("id - " + id);
		}
		return countryResponse;
	}
	
	

}
